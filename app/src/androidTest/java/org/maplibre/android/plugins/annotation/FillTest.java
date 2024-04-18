// This file is generated.

package org.maplibre.android.plugins.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.plugins.BaseActivityTest;
import org.maplibre.android.plugins.annotation.annotation.Fill;
import org.maplibre.android.plugins.annotation.annotation.FillManager;
import org.maplibre.android.plugins.annotation.annotation.FillOptions;
import org.maplibre.android.plugins.testapp.activity.TestActivity;
import org.maplibre.android.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

/**
 * Basic smoke tests for Fill
 */
@RunWith(AndroidJUnit4.class)
public class FillTest extends BaseActivityTest {

    private Fill fill;

    @Override
    protected Class getActivityClass() {
        return TestActivity.class;
    }

    private void setupAnnotation() {
        Timber.i("Retrieving layer");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            FillManager fillManager = new FillManager(idlingResource.getMapView(), maplibreMap, Objects.requireNonNull(maplibreMap.getStyle()));
            List<LatLng> innerLatLngs = new ArrayList<>();
            innerLatLngs.add(new LatLng());
            innerLatLngs.add(new LatLng(1, 1));
            innerLatLngs.add(new LatLng(-1, -1));
            List<List<LatLng>> latLngs = new ArrayList<>();
            latLngs.add(innerLatLngs);
            fill = fillManager.create(new FillOptions().withLatLngs(latLngs));
        });
    }

    @Test
    public void testFillOpacity() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("fill-opacity");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(fill);

            fill.setFillOpacity(2.0f);
            assertEquals((Float) fill.getFillOpacity(), (Float) 2.0f);
        });
    }

    @Test
    public void testFillColor() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("fill-color");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(fill);

            fill.setFillColor("rgba(0, 0, 0, 1)");
            assertEquals(fill.getFillColor(), "rgba(0, 0, 0, 1)");
        });
    }

    @Test
    public void testFillColorAsInt() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("fill-color");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(fill);
            fill.setFillColor(ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
            assertEquals(fill.getFillColorAsInt(), ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
        });
    }


    @Test
    public void testFillOutlineColor() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("fill-outline-color");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(fill);

            fill.setFillOutlineColor("rgba(0, 0, 0, 1)");
            assertEquals(fill.getFillOutlineColor(), "rgba(0, 0, 0, 1)");
        });
    }

    @Test
    public void testFillOutlineColorAsInt() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("fill-outline-color");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(fill);
            fill.setFillOutlineColor(ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
            assertEquals(fill.getFillOutlineColorAsInt(), ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
        });
    }


    @Test
    public void testFillPattern() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("fill-pattern");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(fill);

            fill.setFillPattern("pedestrian-polygon");
            assertEquals((String) fill.getFillPattern(), (String) "pedestrian-polygon");
        });
    }
}
