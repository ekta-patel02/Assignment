package com.example.assignment

import android.app.Activity
import android.app.Application
import android.app.Service
import com.example.assignment.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject


class AssignmentApp: Application(), HasActivityInjector, HasServiceInjector {
    lateinit var dispatchingServiceAndroidInjector: DispatchingAndroidInjector<Service>
        @Inject set

    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
        @Inject set

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return dispatchingServiceAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)

        if(BuildConfig.DEBUG){
            //turn on logs
        }
    }

}