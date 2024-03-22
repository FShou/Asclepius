package com.dicoding.asclepius.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import java.util.Date

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history: History)

    @Delete
    suspend fun deleteHistory(history: History)

    @Query("Select exists (SELECT * from History where date_time = :date)")
    @TypeConverters(DateConverter::class)
    suspend fun isSaved(date: Date): Boolean

    @Query("SELECT * from History order by id_history desc")
    fun getHistories(): LiveData<List<History>>
}