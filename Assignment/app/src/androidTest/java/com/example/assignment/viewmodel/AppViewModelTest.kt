package com.example.assignment.viewmodel

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignment.database.AppDatabase
import com.example.assignment.model.ListData
import com.example.assignment.utils.NetworkUtils
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppViewModelTest : TestCase() {

    private lateinit var context: Application
    private lateinit var appViewModel: AppViewModel

    @Before
    public override fun setUp() {
        context = ApplicationProvider.getApplicationContext<Application>()
        appViewModel = AppViewModel(context)
        appViewModel.getAllAppData()
    }

    @Test
    fun checkInternetConnectivity() {
        val isConnect = NetworkUtils.hasNetwork(context)
        assertThat(isConnect).isTrue()
    }

    @Test
    fun testDataSavedInDB() {
        val db =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
                .build()
        val listData = ListData(1, "TestTitle", "TestDescription", null)
        val list = ArrayList<ListData>()
        list.add(listData)
        val listDao = db.listDataDao()
        val index = listDao.insert(list)
        assertThat(index.isNotEmpty()).isTrue()
    }

}