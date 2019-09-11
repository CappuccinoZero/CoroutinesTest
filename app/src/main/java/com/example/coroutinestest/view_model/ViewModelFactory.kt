package com.example.coroutinestest.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(app:Application) : ViewModelProvider.NewInstanceFactory(){
    val app = app
    @Suppress("UNCHECKED_CASK")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyViewModel(app) as T
    }
}