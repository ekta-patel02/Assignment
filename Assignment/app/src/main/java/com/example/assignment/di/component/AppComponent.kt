package com.example.assignment.di.component

import android.app.Application
import com.example.assignment.AssignmentApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class])
interface AppComponent {

    fun inject(assignmentApp: AssignmentApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}