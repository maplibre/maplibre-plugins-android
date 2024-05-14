package org.maplibre.android.plugins.maps

import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.toLatLng
import org.maplibre.android.maps.toPoint
import org.maplibre.geojson.Point

@RunWith(JUnit4::class)
class GeometryTest {

    @Test
    fun toPoint() {
        val expectedPoint = Point.fromLngLat(25.0, 10.0)
        val latLng = LatLng(10.0, 25.0)
        Assert.assertEquals("Point should match", expectedPoint, latLng.toPoint())
    }

    @Test
    fun toLatLng() {
        val expectedLatLng = LatLng(25.0, 10.0)
        val point = Point.fromLngLat(10.0, 25.0)
        Assert.assertEquals("LatLng should match", expectedLatLng, point.toLatLng())
    }
}
