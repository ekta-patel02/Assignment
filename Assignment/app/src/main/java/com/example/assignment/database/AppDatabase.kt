package com.example.assignment.database

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.assignment.model.ListData
import com.example.assignment.utils.Urls
import timber.log.Timber
import java.util.concurrent.Executors

@Database(entities = [ListData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listdataDao(): ListDataDao
    internal class PopulateDbAsyn(appDatabase: AppDatabase?) : AsyncTask<Void?, Void?, Void?>() {
        private val listDataDao: ListDataDao = appDatabase!!.listdataDao()

        override fun doInBackground(vararg p0: Void?): Void? {
            listDataDao.deleteAll()
            return null
        }
    }

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

        var callback: Callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsyn(INSTANCE)
            }

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Timber.d("Created database")
            }
        }
    }
}