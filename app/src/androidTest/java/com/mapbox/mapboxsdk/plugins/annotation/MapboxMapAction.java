package com.mapbox.mapboxsdk.plugins.annotation;

import android.view.View;

import org.maplibre.android.maps.MapLibreMap;

import org.hamcrest.Matcher;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import org.maplibre.android.maps.MapLibreMap;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MapboxMapAction implements ViewAction {

    private OnInvokeActionListener invokeViewAction;
    private MapLibreMap mapboxMap;

    public MapboxMapAction(OnInvokeActionListener invokeViewAction, MapLibreMap mapboxMap) {
        this.invokeViewAction = invokeViewAction;
        this.mapboxMap = mapboxMap;
    }

    @Override
    public Matcher<View> getConstraints() {
        return isDisplayed();
    }

    @Override
    public String getDescription() {
        return getClass().getSimpleName();
    }

    @Override
    public void perform(UiController uiController, View view) {
        invokeViewAction.onInvokeAction(uiController, mapboxMap);
    }

    public static void invoke(MapLibreMap mapboxMap, OnInvokeActionListener invokeViewAction) {
        onView(withId(android.R.id.content)).perform(new MapboxMapAction(invokeViewAction, mapboxMap));
    }

    public interface OnInvokeActionListener {
        void onInvokeAction(UiController uiController, MapLibreMap mapboxMap);
    }
}


