package com.example.assignment.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.assignment.AssignmentApp
import com.example.assignment.di.component.AppComponent
import com.example.assignment.di.injectors.HasSupportActivityInjector
import com.example.assignment.di.injectors.Injectable
import dagger.android.AndroidInjection
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector : Application.ActivityLifecycleCallbacks {

    fun init(assignmentApp: AssignmentApp) {
        DaggerAppComponent.builder().application(assignmentApp).build().inject(assignmentApp)
        assignmentApp.registerActivityLifecycleCallbacks(this@AppInjector)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        handleActivity(activity)
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

    private fun handleActivity(activity: Activity?) {
        if (activity is HasSupportFragmentInjector || activity is HasSupportActivityInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(object :
                FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentCreated(
                    fm: FragmentManager,
                    f: Fragment,
                    savedInstanceState: Bundle?
                ) {
                    if (f is Injectable) {
                        AndroidSupportInjection.inject(f)
                    }
                }
            }, true)
        }
    }
}