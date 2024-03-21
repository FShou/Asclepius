package com.dicoding.asclepius.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo( name = "id_history")
    var id : Int,

    @ColumnInfo(name = "label")
    var label: String,

    @ColumnInfo(name = "score")
    var score: Float,

    @ColumnInfo(name = "img_uri")
    var imgUri : String,

    @ColumnInfo(name = "date_time")
    var dateTime: String
)