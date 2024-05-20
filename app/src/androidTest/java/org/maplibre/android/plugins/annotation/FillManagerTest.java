// This file is generated.

package org.maplibre.android.plugins.annotation;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.plugins.testapp.activity.TestActivity;
import org.maplibre.android.plugins.BaseActivityTest;

import timber.log.Timber;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static org.maplibre.android.plugins.annotation.MapLibreMapAction.invoke;
import static org.junit.Assert.*;
import static org.maplibre.android.style.layers.Property.*;

/**
 * Basic smoke tests for FillManager
 */
@RunWith(AndroidJUnit4.class)
public class FillManagerTest extends BaseActivityTest {

    private FillManager fillManager;

    @Override
    protected Class getActivityClass() {
        return TestActivity.class;
    }

    private void setupFillManager() {
        Timber.i("Retrieving layer");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            fillManager = new FillManager(idlingResource.getMapView(), maplibreMap, Objects.requireNonNull(maplibreMap.getStyle()));
        });
    }

    @Test
    public void testFillAntialiasAsConstant() {
        validateTestSetup();
        setupFillManager();
        Timber.i("fill-antialias");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(fillManager);

            fillManager.setFillAntialias(true);
            assertEquals((Boolean) fillManager.getFillAntialias(), (Boolean) true);
        });
    }

    @Test
    public void testFillTranslateAsConstant() {
        validateTestSetup();
        setupFillManager();
        Timber.i("fill-translate");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(fillManager);

            fillManager.setFillTranslate(new Float[]{0f, 0f});
            assertEquals((Float[]) fillManager.getFillTranslate(), (Float[]) new Float[]{0f, 0f});
        });
    }

    @Test
    public void testFillTranslateAnchorAsConstant() {
        validateTestSetup();
        setupFillManager();
        Timber.i("fill-translate-anchor");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(fillManager);

            fillManager.setFillTranslateAnchor(FILL_TRANSLATE_ANCHOR_MAP);
            assertEquals((String) fillManager.getFillTranslateAnchor(), (String) FILL_TRANSLATE_ANCHOR_MAP);
        });
    }
}
