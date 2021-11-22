package com.example.assignment.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignment.model.ListData
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest: TestCase(){
    private lateinit var db: AppDatabase
    private lateinit var dao: ListDataDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.listDataDao()
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun writeData(){
        val listData = ListData(1,"TestTitle","TestDescription",null)
        val list = ArrayList<ListData>()
        list.add(listData)
        val index = dao.insert(list)
        assertThat(index.isNotEmpty()).isTrue()
    }
}