package com.dicoding.asclepius.view.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.AppRepository
import com.dicoding.asclepius.data.remote.ArticlesItem
import kotlinx.coroutines.launch

class NewsViewModel (val appRepository: AppRepository) : ViewModel() {

    private val _articleList = MutableLiveData<List<ArticlesItem>>()
    val articleList : LiveData<List<ArticlesItem>> = _articleList

    init {
        getNews()
    }

    fun getNews() = viewModelScope.launch {
        _articleList.value = appRepository.getNews() as List<ArticlesItem>?
    }
}