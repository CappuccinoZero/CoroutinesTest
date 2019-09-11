# 段子阅读器 2
## 使用Kotlin协程来替换RxJava2设计的Mvvm模式简单Demo


在使用Retrofit获得网络响应后，可以通过如下处理
```
    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine {
            enqueue(object :Callback<T>{
                override fun onResponse(
                    call: Call<T>,
                    response: retrofit2.Response<T>
                ) {
                    val bean = response.body()
                    if(bean==null){
                        it.resume(bean.data!!)
                    }else{
                        it.resumeWithException(RuntimeException("BodyNullException"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }
```

或许也可以通过它来达到rxjava map的功能
```
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
```

在viewmodel中，  通过使用viewmodelScope.launch就可以解决它可能带来内存泄露问题
```
    private fun launch(block:suspend ()->Unit,error: suspend (error:Throwable) -> Unit) = viewModelScope.launch{
        try {
            block()
        } catch (e: Exception) {
            error(e)
        }
    }
```

再结合上LiveData<T>
```
  private fun launch(block:suspend ()->Unit) = viewModelScope.launch{
        try {
            block()
        } catch (e: Exception) {
            error.value = e
        }
    }
```
