package com.example.assignment.repository

import com.example.assignment.model.AppData
import com.example.assignment.utils.Urls
import retrofit2.Call
import retrofit2.http.GET

interface AppDataApi {
    @GET(Urls.getAssignmentDataUrl)
    fun getAssignmentData(): Call<AppData?>?
}