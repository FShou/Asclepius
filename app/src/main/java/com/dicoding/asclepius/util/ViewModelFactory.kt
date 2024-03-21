package com.dicoding.asclepius.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.AppRepository
import com.dicoding.asclepius.di.Injection
import com.dicoding.asclepius.view.history.HistoryViewModel
import com.dicoding.asclepius.view.result.ResultViewModel


class ViewModelFactory private constructor(private val appRepository: AppRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HistoryViewModel::class.java)-> return HistoryViewModel(appRepository) as T
            modelClass.isAssignableFrom(ResultViewModel::class.java)-> return ResultViewModel(appRepository) as T
        }

        throw IllegalArgumentException("Unkown ViewModel")
    }

    companion object {
        @Volatile
        private var instance : ViewModelFactory? = null
                fun getInstance(context: Context): ViewModelFactory =
                    instance ?: synchronized(this) {
                        instance ?: ViewModelFactory(Injection.provideRepository(context))
                    }.also { instance = it }

    }
}