package com.dicoding.asclepius.data

import com.dicoding.asclepius.data.local.History
import com.dicoding.asclepius.data.local.HistoryDao
import com.dicoding.asclepius.data.remote.ApiService
import com.dicoding.asclepius.data.remote.ArticlesItem
import java.util.Date

class AppRepository private constructor(
    private val historyDao: HistoryDao,
    private val  apiService: ApiService
) {


    fun getHistories() = historyDao.getHistories()
    suspend fun addHistory(history: History) =  historyDao.insertHistory(history)

    suspend fun deleteHistory(history: History) = historyDao.deleteHistory(history)
    suspend fun isSaved(date: Date) = historyDao.isSaved(date)




    suspend fun getNews(): List<ArticlesItem?>? {
        val response = apiService.getNews()
        return response.articles
    }

    companion object {
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(
            historyDao: HistoryDao,
            apiService: ApiService
        ): AppRepository = instance ?: synchronized(this) {
            instance ?: AppRepository( historyDao, apiService )
        }.also { instance = it }

    }
}