// This file is generated.

package org.maplibre.android.plugins.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.plugins.BaseActivityTest;
import org.maplibre.android.plugins.annotation.annotation.Circle;
import org.maplibre.android.plugins.annotation.annotation.CircleManager;
import org.maplibre.android.plugins.annotation.annotation.CircleOptions;
import org.maplibre.android.plugins.testapp.activity.TestActivity;
import org.maplibre.android.utils.ColorUtils;

import java.util.Objects;

import timber.log.Timber;

/**
 * Basic smoke tests for Circle
 */
@RunWith(AndroidJUnit4.class)
public class CircleTest extends BaseActivityTest {

    private Circle circle;

    @Override
    protected Class getActivityClass() {
        return TestActivity.class;
    }

    private void setupAnnotation() {
        Timber.i("Retrieving layer");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            CircleManager circleManager = new CircleManager(idlingResource.getMapView(), maplibreMap, Objects.requireNonNull(maplibreMap.getStyle()));
            circle = circleManager.create(new CircleOptions().withLatLng(new LatLng()));
        });
    }

    @Test
    public void testCircleRadius() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("circle-radius");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circle);

            circle.setCircleRadius(2.0f);
            assertEquals((Float) circle.getCircleRadius(), (Float) 2.0f);
        });
    }

    @Test
    public void testCircleColor() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("circle-color");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circle);

            circle.setCircleColor("rgba(0, 0, 0, 1)");
            assertEquals(circle.getCircleColor(), "rgba(0, 0, 0, 1)");
        });
    }

    @Test
    public void testCircleColorAsInt() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("circle-color");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circle);
            circle.setCircleColor(ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
            assertEquals(circle.getCircleColorAsInt(), ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
        });
    }


    @Test
    public void testCircleBlur() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("circle-blur");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circle);

            circle.setCircleBlur(2.0f);
            assertEquals((Float) circle.getCircleBlur(), (Float) 2.0f);
        });
    }

    @Test
    public void testCircleOpacity() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("circle-opacity");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circle);

            circle.setCircleOpacity(2.0f);
            assertEquals((Float) circle.getCircleOpacity(), (Float) 2.0f);
        });
    }

    @Test
    public void testCircleStrokeWidth() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("circle-stroke-width");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circle);

            circle.setCircleStrokeWidth(2.0f);
            assertEquals((Float) circle.getCircleStrokeWidth(), (Float) 2.0f);
        });
    }

    @Test
    public void testCircleStrokeColor() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("circle-stroke-color");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circle);

            circle.setCircleStrokeColor("rgba(0, 0, 0, 1)");
            assertEquals(circle.getCircleStrokeColor(), "rgba(0, 0, 0, 1)");
        });
    }

    @Test
    public void testCircleStrokeColorAsInt() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("circle-stroke-color");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circle);
            circle.setCircleStrokeColor(ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
            assertEquals(circle.getCircleStrokeColorAsInt(), ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
        });
    }


    @Test
    public void testCircleStrokeOpacity() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("circle-stroke-opacity");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circle);

            circle.setCircleStrokeOpacity(2.0f);
            assertEquals((Float) circle.getCircleStrokeOpacity(), (Float) 2.0f);
        });
    }
}
