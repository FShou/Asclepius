package com.dicoding.asclepius.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Entity
@TypeConverters(DateConverter::class)
@Parcelize
data class History (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo( name = "id_history")
    var id : Int = 0,

    @ColumnInfo(name = "label")
    var label: String,

    @ColumnInfo(name = "score")
    var score: Float,

    @ColumnInfo(name = "img_uri")
    var imgUri : String,

    @ColumnInfo(name = "date_time")
    var dateTime: Date = Calendar.getInstance(Locale.getDefault()).time
): Parcelable