package com.dicoding.asclepius.view.news

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.AppRepository

class NewsViewModel (val appRepository: AppRepository) : ViewModel() {

    fun getNews() = appRepository.getNews()
}