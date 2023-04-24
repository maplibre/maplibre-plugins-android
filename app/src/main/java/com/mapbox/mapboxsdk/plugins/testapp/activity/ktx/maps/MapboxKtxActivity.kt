package com.mapbox.mapboxsdk.plugins.testapp.activity.ktx.maps

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.maps.queryRenderedFeatures
import com.mapbox.mapboxsdk.plugins.testapp.R
import com.mapbox.mapboxsdk.plugins.testapp.databinding.ActivityMapsKtxBinding

class MapboxKtxActivity : AppCompatActivity(), OnMapReadyCallback, MapboxMap.OnMapClickListener {

    private var mapboxMap: MapboxMap? = null

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

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(Style.getPredefinedStyle("Streets")) {
            mapboxMap.addOnMapClickListener(this)
            Toast.makeText(this, "Click on the map", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapClick(point: LatLng): Boolean {
        val features = mapboxMap?.queryRenderedFeatures(point)
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
        mapboxMap?.removeOnMapClickListener(this)
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
