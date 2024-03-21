package com.dicoding.asclepius.view.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.AppRepository
import com.dicoding.asclepius.data.local.History
import kotlinx.coroutines.launch

class HistoryViewModel (private val appRepository: AppRepository): ViewModel() {

    // TODO: Implement the ViewModel

    fun getHistories() = liveData<List<History>> {
        appRepository.getHistories()
    }

    fun addHistory(history: History) = viewModelScope.launch {
        appRepository.addHistory(history)
    }

    fun delete(history: History) = viewModelScope.launch {
        appRepository.deleteHistory(history)
    }
}