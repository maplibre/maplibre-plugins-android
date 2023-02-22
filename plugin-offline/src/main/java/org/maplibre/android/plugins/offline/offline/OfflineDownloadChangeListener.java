package org.maplibre.android.plugins.offline.offline;

import org.maplibre.android.plugins.offline.model.OfflineDownloadOptions;

public interface OfflineDownloadChangeListener {

  void onCreate(OfflineDownloadOptions offlineDownload);

  void onSuccess(OfflineDownloadOptions offlineDownload);

  void onCancel(OfflineDownloadOptions offlineDownload);

  void onError(OfflineDownloadOptions offlineDownload, String error, String message);

  void onProgress(OfflineDownloadOptions offlineDownload, int progress);

}
