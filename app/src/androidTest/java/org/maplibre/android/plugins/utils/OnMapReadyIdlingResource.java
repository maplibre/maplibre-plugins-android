package org.maplibre.android.plugins.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.mapbox.mapboxsdk.plugins.testapp.R;

import androidx.annotation.NonNull;
import androidx.test.espresso.IdlingResource;

import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.MapView;
import org.maplibre.android.maps.OnMapReadyCallback;
import org.maplibre.android.maps.Style;

public class OnMapReadyIdlingResource implements IdlingResource, OnMapReadyCallback {

    private MapLibreMap mapLibreMap;
    private MapView mapView;
    private IdlingResource.ResourceCallback resourceCallback;

    public OnMapReadyIdlingResource(Activity activity) {
        new Handler(Looper.getMainLooper()).post(() -> {
            mapView = activity.findViewById(R.id.mapView);
            if (mapView != null) {
                mapView.getMapAsync(OnMapReadyIdlingResource.this);
            }
        });
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        return mapLibreMap != null && mapLibreMap.getStyle() != null && mapLibreMap.getStyle().isFullyLoaded();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    public MapView getMapView() {
        return mapView;
    }

    public MapLibreMap getMapLibreMap() {
        return mapLibreMap;
    }

    @Override
    public void onMapReady(@NonNull MapLibreMap mapLibreMap) {
        this.mapLibreMap = mapLibreMap;
        mapLibreMap.setStyle(Style.getPredefinedStyle("Streets"), style -> {
            if (resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
        });
    }
}