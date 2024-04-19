package org.maplibre.android.plugins.annotation;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;
import org.maplibre.android.maps.MapLibreMap;

public class MapLibreMapAction implements ViewAction {

    private OnInvokeActionListener invokeViewAction;
    private MapLibreMap mapLibreMap;

    public MapLibreMapAction(OnInvokeActionListener invokeViewAction, MapLibreMap mapLibreMap) {
        this.invokeViewAction = invokeViewAction;
        this.mapLibreMap = mapLibreMap;
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
        invokeViewAction.onInvokeAction(uiController, mapLibreMap);
    }

    public static void invoke(MapLibreMap mapLibreMap, OnInvokeActionListener invokeViewAction) {
        onView(withId(android.R.id.content)).perform(new MapLibreMapAction(invokeViewAction, mapLibreMap));
    }

    public interface OnInvokeActionListener {
        void onInvokeAction(UiController uiController, MapLibreMap mapLibreMap);
    }
}


