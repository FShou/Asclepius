package com.dicoding.asclepius.view.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.AppRepository
import com.dicoding.asclepius.data.local.History
import kotlinx.coroutines.launch

class HistoryViewModel (private val appRepository: AppRepository): ViewModel() {
    fun getHistories() = appRepository.getHistories()

    fun delete(history: History) = viewModelScope.launch {
        appRepository.deleteHistory(history)
    }
}