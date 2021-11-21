package com.example.assignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.assignment.model.ListData
import com.example.assignment.utils.Urls
import timber.log.Timber

@Database(entities = [ListData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listDataDao(): ListDataDao

    companion object {
        private const val DATABASE_NAME = Urls.DB_NAME

        @Volatile
        var INSTANCE: AppDatabase? = null
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
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Timber.e("==open db call Delete data===")
            }

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Timber.d("Created database")
            }
        }
    }
}