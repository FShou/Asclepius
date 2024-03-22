package com.dicoding.asclepius.data.local

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

    @TypeConverter
    fun toDate(long: Long?): Date? = long?.let { Date(it) }

    @TypeConverter
    fun toLong(date: Date?): Long? = date?.time
}