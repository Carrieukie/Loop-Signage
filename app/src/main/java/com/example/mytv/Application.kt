package com.example.mytv

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.mytv.utils.UtilityFunctions
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class Application : Application() {


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

                val orientation = UtilityFunctions.getOrientation(
                    sharedPreferences.getString(
                        "orientation",
                        "Landscape"
                    )!!
                )
                activity.requestedOrientation = orientation

            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }

        })

    }

}