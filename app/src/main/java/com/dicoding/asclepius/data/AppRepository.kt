package com.dicoding.asclepius.data

import com.dicoding.asclepius.data.local.History
import com.dicoding.asclepius.data.local.HistoryDao
import java.util.Date

class AppRepository(
    val historyDao: HistoryDao
) {


    fun getHistories() = historyDao.getHistories()
    suspend fun addHistory(history: History) =  historyDao.insertHistory(history)

    suspend fun deleteHistory(history: History) = historyDao.deleteHistory(history)
    suspend fun isSaved(date: Date) = historyDao.isSaved(date)


    // Todo: Remote Data


    companion object {
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(
            historyDao: HistoryDao,
        ): AppRepository = instance ?: synchronized(this) {
            instance ?: AppRepository( historyDao )
        }.also { instance = it }

    }
}