// This file is generated.

package org.maplibre.androidk.plugins.annotation;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mapbox.mapboxsdk.plugins.annotation.CircleManager;
import org.maplibre.android.plugins.testapp.activity.TestActivity;
import org.maplibre.androidk.plugins.BaseActivityTest;

import timber.log.Timber;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static org.maplibre.androidk.plugins.annotation.MapboxMapAction.invoke;
import static org.junit.Assert.*;
import static org.maplibre.android.style.layers.Property.*;

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
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            circleManager = new CircleManager(idlingResource.getMapView(), maplibreMap, Objects.requireNonNull(maplibreMap.getStyle()));
        });
    }

    @Test
    public void testCircleTranslateAsConstant() {
        validateTestSetup();
        setupCircleManager();
        Timber.i("circle-translate");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
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
        invoke(maplibreMap, (uiController, maplibreMap) -> {
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
        invoke(maplibreMap, (uiController, maplibreMap) -> {
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
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(circleManager);

            circleManager.setCirclePitchAlignment(CIRCLE_PITCH_ALIGNMENT_MAP);
            assertEquals((String) circleManager.getCirclePitchAlignment(), (String) CIRCLE_PITCH_ALIGNMENT_MAP);
        });
    }
}
