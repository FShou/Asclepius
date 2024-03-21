package com.dicoding.asclepius.view.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.AppRepository
import com.dicoding.asclepius.data.local.History
import kotlinx.coroutines.launch

class ResultViewModel( private val appRepository: AppRepository) : ViewModel() {
    fun addHistory(history: History) = viewModelScope.launch {
        appRepository.addHistory(history)
    }

}