package com.example.assignment.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.assignment.database.AppDatabase
import com.example.assignment.database.ListDataDao
import com.example.assignment.model.ListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppRepository(application: Application) {

    var listDataDao: ListDataDao?
    private var allListData: LiveData<List<ListData?>?>?
    private val database: AppDatabase? = AppDatabase.getInstance(application)

    fun insert(lists: List<ListData?>?) {
        CoroutineScope(Dispatchers.IO).launch {
            listDataDao?.insert(lists)
        }
    }

    fun deleteData() {
        CoroutineScope(Dispatchers.IO).launch {
            listDataDao?.deleteAll()
        }
    }

    val getAllListData: LiveData<List<ListData?>?>?
        get() = allListData

    init {
        listDataDao = database?.listDataDao()
        allListData = listDataDao?.getAllData()
    }
}