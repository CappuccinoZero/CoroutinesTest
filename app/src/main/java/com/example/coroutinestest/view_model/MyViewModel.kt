package com.example.coroutinestest.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coroutinestest.model.Repository
import com.example.coroutinestest.model.data.Data
import com.example.coroutinestest.model.data.LoveEntity
import kotlinx.coroutines.launch

class MyViewModel(app:Application):AndroidViewModel(app) {

    private val model: Repository by lazy {
        Repository.getInstance(app)
    }
    val newDatas by lazy {
        MutableLiveData<ArrayList<Data>>()
    }
    val error by lazy {
        MutableLiveData<Throwable>()
    }
    private var page = 0
    fun onloadTextData(){
        launch({
            newDatas.value = model.onLoadTextData(page)},{Toast.makeText(getApplication(),it.message,Toast.LENGTH_LONG).show()})
    }

    fun onLoadPictureData(){
        launch {
            newDatas.value = model.onLoadPictureData(page)}
    }

    fun onLoveByTest(id:String){
        model.insert(LoveEntity().also {
            it.hate = false
            it.love = true
            it.id = id
        })
    }

    private fun launch(block:suspend ()->Unit,error: suspend (error:Throwable) -> Unit) = viewModelScope.launch{
        try {
            block()
        } catch (e: Exception) {
            error(e)
        }
    }

    private fun launch(block:suspend ()->Unit) = viewModelScope.launch{
        try {
            block()
        } catch (e: Exception) {
            error.value = e
        }
    }
}