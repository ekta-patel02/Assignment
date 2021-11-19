package com.example.assignment.di.modules

import com.example.assignment.model.ListData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ListDataModule {

    @Provides
    @Singleton
    fun provideListData(): ListData{
        return ListData()
    }
}