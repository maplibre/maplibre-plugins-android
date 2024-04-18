package org.maplibre.android.plugins.scalebar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.annotation.VisibleForTesting;

import org.maplibre.android.camera.CameraPosition;
import org.maplibre.android.log.Logger;
import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.MapView;
import org.maplibre.android.maps.Projection;

/**
 * Plugin class that shows a scale bar on MapView and changes the scale corresponding to the MapView's scale.
 */
public class ScaleBarPlugin {
    private static final String TAG = "Mbgl-ScaleBarPlugin";

    private final MapView mapView;
    private final MapLibreMap mapLibreMap;
    private final Projection projection;
    private boolean enabled = true;
    private ScaleBarWidget scaleBarWidget;

    @VisibleForTesting
    final MapLibreMap.OnCameraMoveListener cameraMoveListener = new MapLibreMap.OnCameraMoveListener() {
        @Override
        public void onCameraMove() {
            invalidateScaleBar();
        }
    };

    @VisibleForTesting
    final MapLibreMap.OnCameraIdleListener cameraIdleListener = new MapLibreMap.OnCameraIdleListener() {
        @Override
        public void onCameraIdle() {
            invalidateScaleBar();
        }
    };

    public ScaleBarPlugin(@NonNull MapView mapView, @NonNull MapLibreMap mapLibreMap) {
        this.mapView = mapView;
        this.mapLibreMap = mapLibreMap;
        this.projection = mapLibreMap.getProjection();
    }

    /**
     * Create a scale bar widget on mapView.
     *
     * @param option The scale bar widget options that used to build scale bar widget.
     * @return The created ScaleBarWidget instance.
     */
    public ScaleBarWidget create(@NonNull ScaleBarOptions option) {
        if (scaleBarWidget != null) {
            mapView.removeView(scaleBarWidget);
        }
        scaleBarWidget = option.build();
        scaleBarWidget.setMapViewWidth(mapView.getWidth());
        mapView.addView(scaleBarWidget);

        scaleBarWidget.setVisibility(enabled ? View.VISIBLE : View.GONE);
        if (enabled) {
            addCameraListeners();
            invalidateScaleBar();
        }
        return scaleBarWidget;
    }

    /**
     * Returns true if the scale plugin is currently enabled and visible.
     *
     * @return true if enabled, false otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Toggles the scale plugin state.
     * <p>
     * If the scale plugin wasn enabled, a {@link org.maplibre.android.maps.MapLibreMap.OnCameraMoveListener}
     * will be added to the {@link MapView} to listen to scale change events to update the state of this plugin. If the
     * plugin was disabled the {@link org.maplibre.android.maps.MapLibreMap.OnCameraMoveListener}
     * will be removed from the map.
     * </p>
     */
    @UiThread
    public void setEnabled(boolean enabled) {
        if (scaleBarWidget == null) {
            Logger.w(TAG, "Create a widget before changing ScalebBarPlugin's state. Ignoring.");
            return;
        }
        if (this.enabled == enabled) {
            // already in correct state
            return;
        }
        this.enabled = enabled;
        scaleBarWidget.setVisibility(enabled ? View.VISIBLE : View.GONE);
        if (enabled) {
            addCameraListeners();
            invalidateScaleBar();
        } else {
            removeCameraListeners();
        }
    }

    private void invalidateScaleBar() {
        CameraPosition cameraPosition = mapLibreMap.getCameraPosition();
        scaleBarWidget.setDistancePerPixel((projection.getMetersPerPixelAtLatitude(cameraPosition.target.getLatitude()))
            / mapView.getPixelRatio());
    }

    private void addCameraListeners() {
        mapLibreMap.addOnCameraMoveListener(cameraMoveListener);
        mapLibreMap.addOnCameraIdleListener(cameraIdleListener);
    }

    private void removeCameraListeners() {
        mapLibreMap.removeOnCameraMoveListener(cameraMoveListener);
        mapLibreMap.removeOnCameraIdleListener(cameraIdleListener);
    }
}
