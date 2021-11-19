package com.example.assignment

import dagger.android.*
import com.example.assignment.di.component.DaggerAppComponent


class AssignmentApp: DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            //turn on logs
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this);
    }

}