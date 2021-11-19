package com.example.assignment.di.component

import android.app.Application
import android.content.Context
import com.example.assignment.AssignmentApp
import com.example.assignment.di.modules.ActivityModule
import com.example.assignment.di.modules.AppModule
import com.example.assignment.di.modules.ServiceModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityModule::class,ServiceModule::class])
interface AppComponent : AndroidInjector<AssignmentApp> {

//    fun inject(assignmentApp: AssignmentApp)

   /* @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }*/

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: Context) : AppComponent
    }
}