package com.dicoding.asclepius.view.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.AppRepository
import com.dicoding.asclepius.data.local.History
import kotlinx.coroutines.launch
import java.util.Date

class ResultViewModel( private val appRepository: AppRepository) : ViewModel() {

    private var _isSavedHistory = MutableLiveData<Boolean>()
    val isSavedHistory: LiveData<Boolean> = _isSavedHistory

    fun addHistory(history: History) = viewModelScope.launch {
        appRepository.addHistory(history)
    }

    fun checkIsSaved(date: Date) = viewModelScope.launch {
        _isSavedHistory.value = appRepository.isSaved(date)
    }

}