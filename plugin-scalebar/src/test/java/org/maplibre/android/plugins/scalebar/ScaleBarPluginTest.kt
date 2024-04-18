package org.maplibre.android.plugins.scalebar

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Projection

class ScaleBarPluginTest {
    @MockK
    lateinit var mapView: MapView

    @MockK
    lateinit var projection: Projection

    @MockK
    lateinit var mapLibreMap: MapLibreMap

    @MockK
    lateinit var scaleBarOptions: ScaleBarOptions

    @MockK
    lateinit var scaleBarWidget: ScaleBarWidget

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var resources: Resources

    @MockK
    lateinit var displayMetrics: DisplayMetrics

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        displayMetrics.density = 2f
        every { mapView.width } returns 1000
        every { CameraPosition.DEFAULT.target?.let { projection.getMetersPerPixelAtLatitude(it.latitude) } } returns 100_000.0
        every { mapLibreMap.projection } returns projection
        every { mapLibreMap.cameraPosition } returns CameraPosition.DEFAULT
        every { scaleBarOptions.build() } returns scaleBarWidget
        every { mapView.context } returns context
        every { mapView.pixelRatio } returns 2f
        every { context.resources } returns resources
        every { resources.displayMetrics } returns displayMetrics
    }

    @Test
    fun sanity() {
        assertNotNull(mapView)
        assertNotNull(mapLibreMap)
        assertNotNull(scaleBarOptions)
        assertNotNull(scaleBarWidget)
    }

    @Test
    fun create_isEnabled() {
        val scaleBarPlugin =
            ScaleBarPlugin(
                mapView,
                mapLibreMap
            )
        scaleBarPlugin.create(scaleBarOptions)

        assertTrue(scaleBarPlugin.isEnabled)
        verify { scaleBarWidget.visibility = View.VISIBLE }
        verify(exactly = 1) { mapLibreMap.addOnCameraMoveListener(scaleBarPlugin.cameraMoveListener) }
        verify(exactly = 1) { mapLibreMap.addOnCameraIdleListener(scaleBarPlugin.cameraIdleListener) }
    }

    @Test
    fun disable() {
        val scaleBarPlugin =
            ScaleBarPlugin(
                mapView,
                mapLibreMap
            )
        scaleBarPlugin.create(scaleBarOptions)
        scaleBarPlugin.isEnabled = false

        assertFalse(scaleBarPlugin.isEnabled)
        verify { scaleBarWidget.visibility = View.GONE }
        verify { mapLibreMap.removeOnCameraMoveListener(scaleBarPlugin.cameraMoveListener) }
        verify { mapLibreMap.removeOnCameraIdleListener(scaleBarPlugin.cameraIdleListener) }
    }

    @Test
    fun enable() {
        val scaleBarPlugin =
            ScaleBarPlugin(
                mapView,
                mapLibreMap
            )
        scaleBarPlugin.create(scaleBarOptions)
        verify(exactly = 1) { mapLibreMap.addOnCameraMoveListener(scaleBarPlugin.cameraMoveListener) }
        verify(exactly = 1) { mapLibreMap.addOnCameraIdleListener(scaleBarPlugin.cameraIdleListener) }
        verify(exactly = 1) { scaleBarWidget.visibility = View.VISIBLE }
        scaleBarPlugin.isEnabled = false
        scaleBarPlugin.isEnabled = true

        assertTrue(scaleBarPlugin.isEnabled)
        verify(exactly = 2) { scaleBarWidget.visibility = View.VISIBLE }
        verify(exactly = 2) { mapLibreMap.addOnCameraMoveListener(scaleBarPlugin.cameraMoveListener) }
        verify(exactly = 2) { mapLibreMap.addOnCameraIdleListener(scaleBarPlugin.cameraIdleListener) }
    }

    @Test
    fun disable_enable_widgetIsNull() {
        val scaleBarPlugin =
            ScaleBarPlugin(
                mapView,
                mapLibreMap
            )
        scaleBarPlugin.isEnabled = false
        scaleBarPlugin.isEnabled = true

        verify(exactly = 0) { mapLibreMap.addOnCameraMoveListener(scaleBarPlugin.cameraMoveListener) }
        verify(exactly = 0) { mapLibreMap.addOnCameraIdleListener(scaleBarPlugin.cameraIdleListener) }
    }

    @Test
    fun disableBeforeCreate_ignoreResults() {
        val scaleBarPlugin =
            ScaleBarPlugin(
                mapView,
                mapLibreMap
            )
        scaleBarPlugin.isEnabled = false
        scaleBarPlugin.create(scaleBarOptions)

        assertTrue(scaleBarPlugin.isEnabled)
        verify { scaleBarWidget.visibility = View.VISIBLE }
        verify(exactly = 1) { mapLibreMap.addOnCameraMoveListener(scaleBarPlugin.cameraMoveListener) }
        verify(exactly = 1) { mapLibreMap.addOnCameraIdleListener(scaleBarPlugin.cameraIdleListener) }
    }

    @Test
    fun toggled_invalidateWidget() {
        val scaleBarPlugin =
            ScaleBarPlugin(
                mapView,
                mapLibreMap
            )
        scaleBarPlugin.create(scaleBarOptions)
        verify(exactly = 1) { mapLibreMap.cameraPosition }
        verify(exactly = 1) { scaleBarWidget.setDistancePerPixel(50_000.0) }
        scaleBarPlugin.isEnabled = false
        scaleBarPlugin.isEnabled = true

        verify(exactly = 2) { mapLibreMap.cameraPosition }
        verify(exactly = 2) { scaleBarWidget.setDistancePerPixel(50_000.0) }
    }
}
