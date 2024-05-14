package com.mapbox.mapboxsdk.plugins.testapp.activity.ktx.maps

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.mapboxsdk.plugins.maps.queryRenderedFeatures
import com.mapbox.mapboxsdk.plugins.testapp.databinding.ActivityMapsKtxBinding
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.OnMapReadyCallback
import org.maplibre.android.maps.Style

class MapboxKtxActivity : AppCompatActivity(), OnMapReadyCallback, MapLibreMap.OnMapClickListener {

    private var maplibreMap: MapLibreMap? = null

    private lateinit var binding: ActivityMapsKtxBinding
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsKtxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapLibreMap) {
        this.maplibreMap = mapboxMap
        mapboxMap.setStyle(Style.getPredefinedStyle("Streets")) {
            mapboxMap.addOnMapClickListener(this)
            Toast.makeText(this, "Click on the map", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapClick(point: LatLng): Boolean {
        val features = maplibreMap?.queryRenderedFeatures(point)
        features?.first().let {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
        return true
    }

    public override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    public override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        maplibreMap?.removeOnMapClickListener(this)
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
