package com.example.coroutinestest.model.data

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableBoolean

class Data :BaseObservable() {
    var t = ""
    var text = ""
    var name = ""
    var create_at = ""
    var love = 0
    var hate = 0
    var comment = 0
    var repost = 0
    var image0 = ""
    var profile_image = ""
    private var isLove:ObservableBoolean = ObservableBoolean()
    fun isLove(love: Boolean){
        isLove.set(love)
    }
    fun isLove():Boolean{
        return isLove.get()
    }
    private var isHate:ObservableBoolean = ObservableBoolean()
    fun isHate(love: Boolean){
        isHate.set(love)
    }
    fun isHate():Boolean{
        return isHate.get()
    }
}