package org.maplibre.android.plugins.testapp.activity.offline

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.maplibre.android.maps.MapView
import org.maplibre.android.offline.OfflineManager
import org.maplibre.android.offline.OfflineRegion
import org.maplibre.android.offline.OfflineRegionDefinition
import org.maplibre.android.offline.OfflineRegionStatus
import org.maplibre.android.plugins.offline.model.OfflineDownloadOptions
import org.maplibre.android.plugins.offline.offline.OfflineConstants.KEY_BUNDLE
import org.maplibre.android.plugins.offline.offline.OfflineDownloadChangeListener
import org.maplibre.android.plugins.offline.offline.OfflinePlugin
import org.maplibre.android.plugins.offline.utils.OfflineUtils
import org.maplibre.android.plugins.testapp.R
import org.maplibre.android.plugins.testapp.databinding.ActivityOfflineRegionDetailBinding
import timber.log.Timber

/**
 * Activity showing the detail of an offline region.
 *
 *
 * This Activity can bind to the OfflineDownloadService and show progress.
 *
 *
 *
 * This Activity listens to broadcast events related to successful, canceled and errored download.
 *
 */
class OfflineRegionDetailActivity : AppCompatActivity(),
    OfflineDownloadChangeListener {

    private var offlinePlugin: OfflinePlugin? = null
    private var offlineRegion: OfflineRegion? = null
    private var isDownloading: Boolean = false
    private lateinit var mapView: MapView
    private lateinit var binding: ActivityOfflineRegionDetailBinding

    /**
     * Callback invoked when the states of an offline region changes.
     */
    private val offlineRegionStatusCallback = object : OfflineRegion.OfflineRegionStatusCallback {
        override fun onStatus(status: OfflineRegionStatus?) {
            if (status != null) {
                isDownloading = !status.isComplete
            }
            updateFab()
        }

        override fun onError(error: String?) {
            Toast.makeText(
                this@OfflineRegionDetailActivity,
                "Error getting offline region state: $error",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val offlineRegionDeleteCallback = object : OfflineRegion.OfflineRegionDeleteCallback {
        override fun onDelete() {
            Toast.makeText(
                this@OfflineRegionDetailActivity,
                "Region deleted.",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        override fun onError(error: String) {
            binding.fabDelete.isEnabled = true
            Toast.makeText(
                this@OfflineRegionDetailActivity,
                "Error getting offline region state: $error",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_region_detail)
        binding = ActivityOfflineRegionDetailBinding.inflate(layoutInflater)
        mapView = findViewById<View>(R.id.mapView) as MapView
        mapView.onCreate(savedInstanceState)
        offlinePlugin = OfflinePlugin.getInstance(this)

        val bundle = intent.extras
        if (bundle != null) {
            loadOfflineDownload(bundle)
        }

        binding.fabDelete.setOnClickListener { onFabClick(it) }
    }

    private fun loadOfflineDownload(bundle: Bundle) {
        val regionId: Long
        val offlineDownload = bundle.getParcelable<OfflineDownloadOptions>(KEY_BUNDLE)
        regionId = if (offlineDownload != null) {
            // coming from notification
            offlineDownload.uuid()
        } else {
            // coming from list
            bundle.getLong(KEY_REGION_ID_BUNDLE, -1)
        }

        if (regionId != -1L) {
            loadOfflineRegion(regionId)
        }
    }

    private fun loadOfflineRegion(id: Long) {
        OfflineManager.getInstance(this)
            .listOfflineRegions(object : OfflineManager.ListOfflineRegionsCallback {

                override fun onList(offlineRegions: Array<OfflineRegion>?) {
                    if (offlineRegions != null) {
                        for (region in offlineRegions) {
                            if (region.id == id) {
                                offlineRegion = region
                                val definition = region.definition as OfflineRegionDefinition
                                setupUI(definition)
                                return
                            }
                        }
                    }
                }

                override fun onError(error: String) {
                    Timber.e(error)
                }
            })
    }

    private fun updateFab() {
        if (isDownloading) {
            binding.fabDelete.setImageResource(R.drawable.ic_cancel)
            binding.regionState.text = "DOWNLOADING"
        } else {
            binding.fabDelete.setImageResource(R.drawable.ic_delete)
            binding.regionState.text = "DOWNLOADED"
        }
    }

    private fun setupUI(definition: OfflineRegionDefinition) {
        // update map
        mapView?.getMapAsync { maplibreMap ->
            // correct style
            maplibreMap.setOfflineRegionDefinition(definition) { _ ->
                // restrict camera movement
                maplibreMap.setLatLngBoundsForCameraTarget(definition.bounds)

                // update textview data
                offlineRegion?.metadata?.let {
                    binding.regionName.text = OfflineUtils.convertRegionName(it)
                }
                binding.regionStyleUrl.text = definition.styleURL
                binding.regionLatLngBounds.text = definition.bounds.toString()
                binding.regionMinZoom.text = definition.minZoom.toString()
                binding.regionMaxZoom.text = definition.maxZoom.toString()
                offlineRegion?.getStatus(offlineRegionStatusCallback)
            }
        }
    }

    fun onFabClick(view: View) {
        if (offlineRegion != null) {
            if (!isDownloading) {
                // delete download
                offlineRegion?.delete(offlineRegionDeleteCallback)
            } else {
                // cancel download
                val offlineDownload =
                    offlinePlugin?.getActiveDownloadForOfflineRegion(offlineRegion)
                if (offlineDownload != null) {
                    offlinePlugin?.cancelDownload(offlineDownload)
                    isDownloading = false
                }
            }
            view.visibility = View.GONE
        }
    }

    override fun onCreate(offlineDownload: OfflineDownloadOptions) {
        Timber.e("OfflineDownloadOptions created %s", offlineDownload.hashCode())
    }

    override fun onSuccess(offlineDownload: OfflineDownloadOptions) {
        isDownloading = false
        binding.regionStateProgress.visibility = View.INVISIBLE
        updateFab()
    }

    override fun onCancel(offlineDownload: OfflineDownloadOptions) {
        finish() // nothing to do in this screen, cancel = delete
    }

    override fun onError(offlineDownload: OfflineDownloadOptions, error: String, message: String) {
        binding.regionStateProgress.visibility = View.INVISIBLE
        binding.regionState.text = "ERROR"
        Toast.makeText(this, error + message, Toast.LENGTH_LONG).show()
    }

    override fun onProgress(offlineDownload: OfflineDownloadOptions, progress: Int) {
        if (offlineRegion == null) {
            return
        }

        if (offlineDownload.uuid() == offlineRegion?.id) {
            if (binding.regionStateProgress.visibility != View.VISIBLE) {
                binding.regionStateProgress.visibility = View.VISIBLE
            }
            isDownloading = true
            binding.regionStateProgress.progress = progress
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        offlinePlugin?.addOfflineDownloadStateChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        offlinePlugin?.removeOfflineDownloadStateChangeListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {

        val KEY_REGION_ID_BUNDLE = "org.maplibre.android.plugins.offline.bundle.id"
    }
}
