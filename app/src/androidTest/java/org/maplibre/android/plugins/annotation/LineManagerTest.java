// This file is generated.

package org.maplibre.android.plugins.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.maplibre.android.style.layers.Property.LINE_CAP_BUTT;
import static org.maplibre.android.style.layers.Property.LINE_TRANSLATE_ANCHOR_MAP;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.maplibre.android.plugins.BaseActivityTest;
import org.maplibre.android.plugins.annotation.annotation.LineManager;
import org.maplibre.android.plugins.testapp.activity.TestActivity;

import java.util.Objects;

import timber.log.Timber;

/**
 * Basic smoke tests for LineManager
 */
@RunWith(AndroidJUnit4.class)
public class LineManagerTest extends BaseActivityTest {

    private LineManager lineManager;

    @Override
    protected Class getActivityClass() {
        return TestActivity.class;
    }

    private void setupLineManager() {
        Timber.i("Retrieving layer");
        MapLibreMapAction.invoke(mapLibreMap, (uiController, mapLibreMap) -> {
            lineManager = new LineManager(idlingResource.getMapView(), mapLibreMap, Objects.requireNonNull(mapLibreMap.getStyle()));
        });
    }

    @Test
    public void testLineCapAsConstant() {
        validateTestSetup();
        setupLineManager();
        Timber.i("line-cap");
        MapLibreMapAction.invoke(mapLibreMap, (uiController, mapLibreMap) -> {
            assertNotNull(lineManager);

            lineManager.setLineCap(LINE_CAP_BUTT);
            assertEquals((String) lineManager.getLineCap(), (String) LINE_CAP_BUTT);
        });
    }

    @Test
    public void testLineMiterLimitAsConstant() {
        validateTestSetup();
        setupLineManager();
        Timber.i("line-miter-limit");
        MapLibreMapAction.invoke(mapLibreMap, (uiController, mapLibreMap) -> {
            assertNotNull(lineManager);

            lineManager.setLineMiterLimit(2.0f);
            assertEquals((Float) lineManager.getLineMiterLimit(), (Float) 2.0f);
        });
    }

    @Test
    public void testLineRoundLimitAsConstant() {
        validateTestSetup();
        setupLineManager();
        Timber.i("line-round-limit");
        MapLibreMapAction.invoke(mapLibreMap, (uiController, mapLibreMap) -> {
            assertNotNull(lineManager);

            lineManager.setLineRoundLimit(2.0f);
            assertEquals((Float) lineManager.getLineRoundLimit(), (Float) 2.0f);
        });
    }

    @Test
    public void testLineTranslateAsConstant() {
        validateTestSetup();
        setupLineManager();
        Timber.i("line-translate");
        MapLibreMapAction.invoke(mapLibreMap, (uiController, mapLibreMap) -> {
            assertNotNull(lineManager);

            lineManager.setLineTranslate(new Float[]{0f, 0f});
            assertEquals((Float[]) lineManager.getLineTranslate(), (Float[]) new Float[]{0f, 0f});
        });
    }

    @Test
    public void testLineTranslateAnchorAsConstant() {
        validateTestSetup();
        setupLineManager();
        Timber.i("line-translate-anchor");
        MapLibreMapAction.invoke(mapLibreMap, (uiController, mapLibreMap) -> {
            assertNotNull(lineManager);

            lineManager.setLineTranslateAnchor(LINE_TRANSLATE_ANCHOR_MAP);
            assertEquals((String) lineManager.getLineTranslateAnchor(), (String) LINE_TRANSLATE_ANCHOR_MAP);
        });
    }

    @Test
    public void testLineDasharrayAsConstant() {
        validateTestSetup();
        setupLineManager();
        Timber.i("line-dasharray");
        MapLibreMapAction.invoke(mapLibreMap, (uiController, mapLibreMap) -> {
            assertNotNull(lineManager);

            lineManager.setLineDasharray(new Float[]{});
            assertEquals((Float[]) lineManager.getLineDasharray(), (Float[]) new Float[]{});
        });
    }
}
