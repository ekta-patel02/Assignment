package com.example.assignment.di.modules

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }


//    @Provides
//    @Singleton
//    fun provideLoginRepository(loginDataRepository: AccountDataRepository): AccountRepository {
//        return loginDataRepository
//    }

}