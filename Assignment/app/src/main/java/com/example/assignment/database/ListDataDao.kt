package com.example.assignment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment.model.ListData
import com.example.assignment.utils.Urls

/*
* @Dao used to fetch/update/delete/edit data from db
* */
@Dao
interface ListDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listData: List<ListData?>?): List<Long>

    @Query("SELECT * FROM " + Urls.TABLE_NAME)
    fun getAllData(): LiveData<List<ListData?>?>?

    @Query("DELETE FROM " + Urls.TABLE_NAME)
    fun deleteAll()
}