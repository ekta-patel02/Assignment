package com.example.assignment

import android.app.Application
import timber.log.Timber

class AssignmentApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            //turn on logs
            Timber.plant(Timber.DebugTree())
        }
    }
}