package com.example.assignment.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.assignment.database.AppDatabase
import com.example.assignment.database.ListDataDao
import com.example.assignment.model.ListData
import timber.log.Timber

class AppRepository(application: Application) {

    var listDataDao: ListDataDao?
    private var allListData: LiveData<List<ListData?>?>?
    private val database: AppDatabase? = AppDatabase.getInstance(application)

    fun insert(lists: List<ListData?>?) {
        InsertAsyncTask(listDataDao).execute(lists)
    }

    fun deleteData() {
        Timber.e("==Repo Delete data===")
        AppDatabase.DeleteDbAsyn(database).execute()
    }

    val getAllListData: LiveData<List<ListData?>?>?
        get() = allListData

    private class InsertAsyncTask(listDao: ListDataDao?) :
        AsyncTask<List<ListData?>?, Void?, Void?>() {
        private val listDataDao = listDao


        override fun doInBackground(vararg lists: List<ListData?>?): Void? {
            listDataDao?.insert(lists[0])
            Timber.e("==inserted data===")
            return null
        }
    }

    init {
        listDataDao = database?.listdataDao()
        allListData = listDataDao?.getAllData()
    }
}