package com.example.assignment.model

import com.google.gson.annotations.SerializedName

data class AppData(
    @SerializedName("title") val title: String?,
    @SerializedName("rows") val listData: ArrayList<ListData?>?
)