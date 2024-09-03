package org.maplibre.android.plugins.testapp

import android.app.Application
import org.maplibre.android.MapLibre
import timber.log.Timber

class PluginApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeLogger()
        MapLibre.getInstance(this)
    }

    private fun initializeLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
