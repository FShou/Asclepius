package com.dicoding.asclepius.view.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {


    private var _currentImgUri = MutableLiveData<Uri?>()
    val currentImgUri: LiveData<Uri?> = _currentImgUri

    fun setCurrentContentUri(uri: Uri){
        _currentImgUri.value = uri
    }


}