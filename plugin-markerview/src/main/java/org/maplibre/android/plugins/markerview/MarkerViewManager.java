package org.maplibre.android.plugins.markerview;


import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import org.maplibre.android.maps.MapView;
import org.maplibre.android.maps.MapLibreMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for synchronising views at a LatLng on top of a Map.
 */
public class MarkerViewManager implements MapView.OnCameraDidChangeListener, MapView.OnCameraIsChangingListener {

    private final MapView mapView;
    private final MapLibreMap mapLibreMap;
    private final List<MarkerView> markers = new ArrayList<>();
    private boolean initialised;

    /**
     * Create a MarkerViewManager.
     *
     * @param mapView   the MapView used to synchronise views on
     * @param mapLibreMap the MapLibreMap to synchronise views with
     */
    public MarkerViewManager(MapView mapView, MapLibreMap mapLibreMap) {
        this.mapView = mapView;
        this.mapLibreMap = mapLibreMap;
    }

    /**
     * Destroys the MarkerViewManager.
     * <p>
     * Should be called before MapView#onDestroy
     * </p>
     */
    @UiThread
    public void onDestroy() {
        markers.clear();
        mapView.removeOnCameraDidChangeListener(this);
        mapView.removeOnCameraIsChangingListener(this);
        initialised = false;
    }

    /**
     * Add a MarkerView to the map using MarkerView and LatLng.
     *
     * @param markerView the markerView to synchronise on the map
     */
    @UiThread
    public void addMarker(@NonNull MarkerView markerView) {
        if (mapView.isDestroyed() || markers.contains(markerView)) {
            return;
        }

        if (!initialised) {
            initialised = true;
            mapView.addOnCameraDidChangeListener(this);
            mapView.addOnCameraIsChangingListener(this);
        }
        markerView.setProjection(mapLibreMap.getProjection());
        mapView.addView(markerView.getView());
        markers.add(markerView);
        markerView.update();
    }

    /**
     * Remove an existing markerView from the map.
     *
     * @param markerView the markerView to be removed from the map
     */
    @UiThread
    public void removeMarker(@NonNull MarkerView markerView) {
        if (mapView.isDestroyed() || !markers.contains(markerView)) {
            return;
        }

        mapView.removeView(markerView.getView());
        markers.remove(markerView);
    }

    private void update() {
        for (MarkerView marker : markers) {
            marker.update();
        }
    }

    @Override
    public void onCameraDidChange(boolean animated) {
        update();
    }

    @Override
    public void onCameraIsChanging() {
        update();
    }
}
