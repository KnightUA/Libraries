package ua.udevapp.samples.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import ua.udevapp.libraries.BuildConfig
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy


@HiltAndroidApp
class Libraries : Application() {
    override fun onCreate() {
        super.onCreate()

        this.initializeLogger()
        this.enableStrictModeWhen(BuildConfig.DEBUG)
    }

    private fun initializeLogger() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun enableStrictModeWhen(predicate: Boolean) {
        if(predicate) {
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
    }

}