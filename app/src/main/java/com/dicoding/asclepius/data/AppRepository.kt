package com.dicoding.asclepius.data

import com.dicoding.asclepius.data.local.History
import com.dicoding.asclepius.data.local.HistoryDao

class AppRepository(
    val historyDao: HistoryDao
) {

    // Todo: Local Db data
     fun getHistories() = historyDao.getHistories()
    suspend fun addHistory(history: History) =  historyDao.insertHistory(history)

    suspend fun deleteHistory(history: History) = historyDao.deleteHistory(history)




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