package com.example.coroutinestest.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment:Fragment() {
    private var isViewCreated = false
    private var isViewVisibled = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        lazyData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
       if(isVisibleToUser){
           isViewVisibled = true
           lazyData()
       }else
           isViewVisibled = false
    }

    private fun lazyData(){
        if(isViewCreated&&isViewVisibled)
            loadData()
    }
    abstract fun loadData()
}