package com.mapbox.mapboxsdk.plugins.offline.ui;

import org.maplibre.android.offline.OfflineRegionDefinition;

public interface RegionSelectedCallback {

    void onSelected(OfflineRegionDefinition definition, String regionName);

    void onCancel();
}
