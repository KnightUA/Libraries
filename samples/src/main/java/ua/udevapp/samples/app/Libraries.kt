package ua.udevapp.samples.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import ua.udevapp.libraries.BuildConfig

@HiltAndroidApp
class Libraries : Application() {
    override fun onCreate() {
        super.onCreate()

        this.initializeLogger()
    }

    private fun initializeLogger() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

}