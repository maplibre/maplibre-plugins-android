
// This file is generated.

package org.maplibre.android.plugins.annotation;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.plugins.BaseActivityTest;
import org.maplibre.android.plugins.testapp.activity.TestActivity;
import org.maplibre.android.utils.ColorUtils;
import timber.log.Timber;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.maplibre.android.plugins.annotation.MapLibreMapAction.invoke;
import static org.maplibre.android.style.layers.Property.LINE_JOIN_BEVEL;

/**
 * Basic smoke tests for Line
 */
@RunWith(AndroidJUnit4.class)
public class LineTest extends BaseActivityTest {

    private Line line;

    @Override
    protected Class getActivityClass() {
        return TestActivity.class;
    }

    private void setupAnnotation() {
        Timber.i("Retrieving layer");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            LineManager lineManager = new LineManager(idlingResource.getMapView(), maplibreMap, Objects.requireNonNull(maplibreMap.getStyle()));
            List<LatLng> latLngs = new ArrayList<>();
            latLngs.add(new LatLng());
            latLngs.add(new LatLng(1, 1));
            line = lineManager.create(new LineOptions().withLatLngs(latLngs));
        });
    }

    @Test
    public void testLineJoin() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("line-join");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(line);

            line.setLineJoin(LINE_JOIN_BEVEL);
            assertEquals((String) line.getLineJoin(), (String) LINE_JOIN_BEVEL);
        });
    }

    @Test
    public void testLineOpacity() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("line-opacity");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(line);

            line.setLineOpacity(2.0f);
            assertEquals((Float) line.getLineOpacity(), (Float) 2.0f);
        });
    }

    @Test
    public void testLineColor() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("line-color");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(line);

            line.setLineColor("rgba(0, 0, 0, 1)");
            assertEquals(line.getLineColor(), "rgba(0, 0, 0, 1)");
        });
    }

    @Test
    public void testLineColorAsInt() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("line-color");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(line);
            line.setLineColor(ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
            assertEquals(line.getLineColorAsInt(), ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
        });
    }


    @Test
    public void testLineWidth() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("line-width");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(line);

            line.setLineWidth(2.0f);
            assertEquals((Float) line.getLineWidth(), (Float) 2.0f);
        });
    }

    @Test
    public void testLineGapWidth() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("line-gap-width");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(line);

            line.setLineGapWidth(2.0f);
            assertEquals((Float) line.getLineGapWidth(), (Float) 2.0f);
        });
    }

    @Test
    public void testLineOffset() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("line-offset");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(line);

            line.setLineOffset(2.0f);
            assertEquals((Float) line.getLineOffset(), (Float) 2.0f);
        });
    }

    @Test
    public void testLineBlur() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("line-blur");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(line);

            line.setLineBlur(2.0f);
            assertEquals((Float) line.getLineBlur(), (Float) 2.0f);
        });
    }

    @Test
    public void testLinePattern() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("line-pattern");
        invoke(maplibreMap, (uiController, maplibreMap) -> {
            assertNotNull(line);

            line.setLinePattern("pedestrian-polygon");
            assertEquals((String) line.getLinePattern(), (String) "pedestrian-polygon");
        });
    }
}
