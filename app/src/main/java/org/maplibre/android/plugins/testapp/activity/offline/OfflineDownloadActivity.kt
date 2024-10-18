package org.maplibre.android.plugins.testapp.activity.offline

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.maplibre.android.constants.MapLibreConstants
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.geometry.LatLngBounds
import org.maplibre.android.offline.OfflineTilePyramidRegionDefinition
import org.maplibre.android.plugins.offline.model.NotificationOptions
import org.maplibre.android.plugins.offline.model.OfflineDownloadOptions
import org.maplibre.android.plugins.offline.offline.OfflinePlugin
import org.maplibre.android.plugins.offline.utils.OfflineUtils
import org.maplibre.android.plugins.testapp.TestStyles
import org.maplibre.android.plugins.testapp.R
import org.maplibre.android.plugins.testapp.databinding.ActivityOfflineDownloadBinding

/**
 * Activity showing a form to configure the download of an offline region.
 */
class OfflineDownloadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOfflineDownloadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfflineDownloadBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_offline_download)
        initUi()
        initSeekbarListeners()
        binding.fabStartDownload.setOnClickListener {
            if (binding.seekbarMaxZoom.progress.toFloat() > binding.seekbarMinZoom.progress.toFloat()) {
                onDownloadRegion()
            } else {
                Toast.makeText(
                    this,
                    "Please make sure that the Max zoom value is larger" +
                        " than the Min zoom level",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initUi() {
        initEditTexts()
        initSeekbars()
        initSpinner()
        initZoomLevelTextviews()
    }

    private fun initEditTexts() {
        binding.editTextRegionName.setText("Region name")
        binding.editTextLatNorth.setText("40.7589372691904")
        binding.editTextLonEast.setText("-73.96024123810196")
        binding.editTextLatSouth.setText("40.740763489055496")
        binding.editTextLonWest.setText("-73.97569076188057")
    }

    private fun initSeekbars() {
        val maxZoom = MapLibreConstants.MAXIMUM_ZOOM.toInt()
        binding.seekbarMinZoom.max = maxZoom
        binding.seekbarMinZoom.progress = 16
        binding.seekbarMaxZoom.max = maxZoom
        binding.seekbarMaxZoom.progress = 19
    }

    private fun initSpinner() {
        val styles = ArrayList<String>()
        styles.add(TestStyles.BRIGHT.url)
        styles.add(TestStyles.POSITRON.url)
        styles.add(TestStyles.LIBERTY.url)
        val spinnerArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, styles)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerStyleUrl.adapter = spinnerArrayAdapter
    }

    private fun initZoomLevelTextviews() {
        binding.textViewMaxText.text =
            getString(R.string.max_zoom_textview, binding.seekbarMaxZoom.progress)
        binding.textViewMinText.text =
            getString(R.string.min_zoom_textview, binding.seekbarMinZoom.progress)
    }

    private fun initSeekbarListeners() {
        binding.seekbarMaxZoom.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.textViewMaxText.text = getString(R.string.max_zoom_textview, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Empty on purpose
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Empty on purpose
            }
        })

        binding.seekbarMinZoom.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.textViewMinText.text = getString(R.string.min_zoom_textview, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Empty on purpose
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Empty on purpose
            }
        })
    }

    fun onDownloadRegion() {
        // get data from UI
        val regionName = binding.editTextRegionName.text.toString()
        val latitudeNorth = binding.editTextLatNorth.text.toString().toDouble()
        val longitudeEast = binding.editTextLonEast.text.toString().toDouble()
        val latitudeSouth = binding.editTextLatSouth.text.toString().toDouble()
        val longitudeWest = binding.editTextLonWest.text.toString().toDouble()
        val styleUrl = binding.spinnerStyleUrl.selectedItem as String
        val maxZoom = binding.seekbarMaxZoom.progress.toFloat()
        val minZoom = binding.seekbarMinZoom.progress.toFloat()

        if (!validCoordinates(latitudeNorth, longitudeEast, latitudeSouth, longitudeWest)) {
            Toast.makeText(this, "coordinates need to be in valid range", Toast.LENGTH_LONG).show()
            return
        }

        // create offline definition from data
        val definition = OfflineTilePyramidRegionDefinition(
            styleUrl,
            LatLngBounds.Builder()
                .include(LatLng(latitudeNorth, longitudeEast))
                .include(LatLng(latitudeSouth, longitudeWest))
                .build(),
            minZoom.toDouble(),
            maxZoom.toDouble(),
            resources.displayMetrics.density
        )

        // customize notification appearance
        val notificationOptions = NotificationOptions.builder(this)
            .smallIconRes(R.drawable.maplibre_logo_icon)
            .returnActivity(OfflineRegionDetailActivity::class.java.name)
            .build()

        // start offline download
        OfflinePlugin.getInstance(this).startDownload(
            OfflineDownloadOptions.builder()
                .definition(definition)
                .metadata(OfflineUtils.convertRegionName(regionName))
                .notificationOptions(notificationOptions)
                .build()
        )
    }

    private fun validCoordinates(
        latitudeNorth: Double,
        longitudeEast: Double,
        latitudeSouth: Double,
        longitudeWest: Double
    ): Boolean {
        if (latitudeNorth < -90 || latitudeNorth > 90) {
            return false
        } else if (longitudeEast < -180 || longitudeEast > 180) {
            return false
        } else if (latitudeSouth < -90 || latitudeSouth > 90) {
            return false
        } else if (longitudeWest < -180 || longitudeWest > 180) {
            return false
        }
        return true
    }
}
