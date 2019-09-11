package com.example.coroutinestest.model

import com.example.coroutinestest.model.data.ContentBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("satinApi")
    fun getDataItem(@Query("type") type:Int,@Query("page") page:Int): Call<ContentBean>
}