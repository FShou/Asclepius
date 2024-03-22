package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.AppRepository
import com.dicoding.asclepius.data.local.HistoryDatabase
import com.dicoding.asclepius.data.remote.ApiConfig

object Injection {
    fun provideRepository(context: Context): AppRepository{
        val database = HistoryDatabase.getInstance(context)
        val dao = database.historyDao()
        val apiService = ApiConfig.getApiConfig()

        return AppRepository.getInstance(dao,apiService)
    }
}