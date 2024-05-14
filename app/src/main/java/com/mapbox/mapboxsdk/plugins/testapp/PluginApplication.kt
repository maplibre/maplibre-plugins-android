package com.mapbox.mapboxsdk.plugins.testapp

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import org.maplibre.android.MapLibre
import timber.log.Timber

class PluginApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)
        initializeLogger()
        MapLibre.getInstance(this)
    }

    private fun initializeLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
