package com.example.coroutinestest.model

import android.content.Context
import com.example.coroutinestest.model.data.ContentBean
import com.example.coroutinestest.model.data.Data
import com.example.coroutinestest.model.data.LoveEntity
import com.example.coroutinestest.util.MyUtil
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Repository(appContext:Context) {
    companion object{
        private var instance: Repository?= null
        fun getInstance(appContext:Context): Repository {
            if(instance == null){
                synchronized(Repository::class.java){
                    if (instance ==null)
                        instance = Repository(appContext)
                }
            }
            return instance!!
        }
    }
    private val cache_size = 1024*1024*10L
    private var max_age = 60*60*24*3
    private val url = "https://www.apiopen.top/"
    private val room by lazy {
        RoomHelper.getInstance(appContext.applicationContext)
    }
    private val retrofit:Retrofit by lazy {
        Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(CoroutineCallAdapterFactory())
            client(client)
            baseUrl(url)
        }.build()
    }
    private val client:OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            readTimeout(10,TimeUnit.SECONDS)
            connectTimeout(10,TimeUnit.SECONDS)
            writeTimeout(10,TimeUnit.SECONDS)
            addNetworkInterceptor(cacheInterceptor)
            cache(cache)
        }.build()
    }
    private val cacheFile by lazy {
        File(appContext.cacheDir,"httpCache")
    }
    private val cache by lazy {
        Cache(cacheFile,cache_size)
    }

    private val cacheInterceptor = object :Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)
            if(MyUtil.isNetworkConnected(appContext)){
                response.newBuilder().removeHeader("Pragma")
                    .addHeader("Cache-Control","public, max-age=$max_age")
                    .build()
            }
            return response
        }
    }

    suspend fun onLoadPictureData(page:Int) =  retrofit.create(Api::class.java).getDataItem(3,page).await()

    suspend fun onLoadTextData(page:Int) = retrofit.create(Api::class.java).getDataItem(2,page).await()


    fun insert(loveEntity: LoveEntity){
        room.getDao().insertData(loveEntity)
    }

    private suspend fun Call<ContentBean>.await():ArrayList<Data>{
        return suspendCoroutine {
            enqueue(object :Callback<ContentBean>{
                override fun onResponse(
                    call: Call<ContentBean>,
                    response: retrofit2.Response<ContentBean>
                ) {
                    val bean = response.body()
                    if(bean?.data!=null){
                        it.resume(bean.data!!)
                    }else{
                        it.resumeWithException(RuntimeException("BodyNullException"))
                    }
                }

                override fun onFailure(call: Call<ContentBean>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }

}