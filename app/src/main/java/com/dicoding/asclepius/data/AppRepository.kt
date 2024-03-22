package com.dicoding.asclepius.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
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


    // Todo: Remote Data

     fun getNews(): LiveData<List<ArticlesItem?>?> = liveData {
        try {
            val response = apiService.getNews()
            val articles =  response.articles
            emit(articles)
        } catch (e: Exception) {

        }
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