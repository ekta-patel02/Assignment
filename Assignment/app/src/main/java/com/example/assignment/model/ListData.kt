package com.example.assignment.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.assignment.utils.Urls
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = Urls.TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class ListData(
    @NotNull
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String? = null,

    @SerializedName("description")
    @ColumnInfo(name = "description")
    var description: String? = null,

    @SerializedName("imageHref")
    @ColumnInfo(name = "imgHref")
    var imgHref: String? = null
)