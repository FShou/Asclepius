package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.AppRepository
import com.dicoding.asclepius.data.local.HistoryDatabase

object Injection {
    fun provideRepository(context: Context): AppRepository{
        val database = HistoryDatabase.getInstance(context)
        val dao = database.historyDao()

        return AppRepository.getInstance(dao)
    }
}