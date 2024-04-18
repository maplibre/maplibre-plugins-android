// This file is generated.

package org.maplibre.android.plugins.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.maplibre.android.style.layers.Property.CIRCLE_PITCH_ALIGNMENT_MAP;
import static org.maplibre.android.style.layers.Property.CIRCLE_PITCH_SCALE_MAP;
import static org.maplibre.android.style.layers.Property.CIRCLE_TRANSLATE_ANCHOR_MAP;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.maplibre.android.plugins.BaseActivityTest;
import org.maplibre.android.plugins.annotation.annotation.CircleManager;
import org.maplibre.android.plugins.testapp.activity.TestActivity;

import java.util.Objects;

import timber.log.Timber;

/**
 * Basic smoke tests for CircleManager
 */
@RunWith(AndroidJUnit4.class)
public class CircleManagerTest extends BaseActivityTest {

    private CircleManager circleManager;

    @Override
    protected Class getActivityClass() {
        return TestActivity.class;
    }

    private void setupCircleManager() {
        Timber.i("Retrieving layer");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            circleManager = new CircleManager(idlingResource.getMapView(), maplibreMap, Objects.requireNonNull(maplibreMap.getStyle()));
        });
    }

    @Test
    public void testCircleTranslateAsConstant() {
        validateTestSetup();
        setupCircleManager();
        Timber.i("circle-translate");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circleManager);

            circleManager.setCircleTranslate(new Float[]{0f, 0f});
            assertEquals((Float[]) circleManager.getCircleTranslate(), (Float[]) new Float[]{0f, 0f});
        });
    }

    @Test
    public void testCircleTranslateAnchorAsConstant() {
        validateTestSetup();
        setupCircleManager();
        Timber.i("circle-translate-anchor");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circleManager);

            circleManager.setCircleTranslateAnchor(CIRCLE_TRANSLATE_ANCHOR_MAP);
            assertEquals((String) circleManager.getCircleTranslateAnchor(), (String) CIRCLE_TRANSLATE_ANCHOR_MAP);
        });
    }

    @Test
    public void testCirclePitchScaleAsConstant() {
        validateTestSetup();
        setupCircleManager();
        Timber.i("circle-pitch-scale");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circleManager);

            circleManager.setCirclePitchScale(CIRCLE_PITCH_SCALE_MAP);
            assertEquals((String) circleManager.getCirclePitchScale(), (String) CIRCLE_PITCH_SCALE_MAP);
        });
    }

    @Test
    public void testCirclePitchAlignmentAsConstant() {
        validateTestSetup();
        setupCircleManager();
        Timber.i("circle-pitch-alignment");
        MapLibreMapAction.invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circleManager);

            circleManager.setCirclePitchAlignment(CIRCLE_PITCH_ALIGNMENT_MAP);
            assertEquals((String) circleManager.getCirclePitchAlignment(), (String) CIRCLE_PITCH_ALIGNMENT_MAP);
        });
    }
}
