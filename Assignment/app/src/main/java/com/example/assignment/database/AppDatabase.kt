package com.example.assignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignment.model.ListData
import com.example.assignment.utils.Urls

@Database(entities = [ListData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listDataDao(): ListDataDao

    companion object {
        private const val DATABASE_NAME = Urls.DB_NAME
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context?): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Room.databaseBuilder(context!!, AppDatabase::class.java, DATABASE_NAME)
                                .fallbackToDestructiveMigration()
                                .addCallback(callback)
                                .build()
                    }
                }
            }
            return INSTANCE
        }

        private var callback: Callback = object : Callback() {

        }
    }
}