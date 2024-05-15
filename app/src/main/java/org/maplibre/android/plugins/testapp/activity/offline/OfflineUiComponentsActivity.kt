package org.maplibre.android.plugins.testapp.activity.offline

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.maplibre.android.plugins.offline.OfflineRegionSelector
import org.maplibre.android.plugins.offline.model.NotificationOptions
import org.maplibre.android.plugins.offline.model.RegionSelectionOptions
import org.maplibre.android.plugins.offline.offline.OfflinePlugin
import org.maplibre.android.plugins.testapp.R
import org.maplibre.android.plugins.testapp.databinding.ActivityOfflineUiComponentsBinding
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import java.util.Locale

class OfflineUiComponentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOfflineUiComponentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_ui_components)
        binding = ActivityOfflineUiComponentsBinding.inflate(layoutInflater)
        binding.fabRegionSelector.setOnClickListener {
            onOfflineRegionSelectorButtonClicked()
        }
    }

    fun onOfflineRegionSelectorButtonClicked() {
        // Create the offline region selector options
        val options = RegionSelectionOptions.builder()
            .statingCameraPosition(
                CameraPosition.Builder().target(LatLng(32.7852, -96.8154)).zoom(12.0).build()
            ).build()

        val intent = OfflineRegionSelector.IntentBuilder()
            .regionSelectionOptions(options)
            .build(this)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val builder = NotificationOptions.builder(this)
                .contentTitle("Downloading: ")
                .returnActivity(OfflineUiComponentsActivity::class.java.name)
                .smallIconRes(android.R.drawable.stat_sys_download)

            if (data?.let { OfflineRegionSelector.getRegionName(it) } != null) {
                builder.contentText(OfflineRegionSelector.getRegionName(data))
            }

            val options = OfflineRegionSelector.getOfflineDownloadOptions(data, builder.build())
            OfflinePlugin.getInstance(this).startDownload(options)

            Toast.makeText(
                this,
                String.format(
                    Locale.US,
                    "Region name: %s",
                    data?.let { OfflineRegionSelector.getRegionName(it) } ?: "null"
                ),
                Toast.LENGTH_LONG
            ).show()
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "user canceled out of region selector", Toast.LENGTH_LONG).show()
        }
    }

    companion object {

        private val REQUEST_CODE = 9384
    }
}
