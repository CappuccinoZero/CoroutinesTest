package com.example.coroutinestest.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class DataBindingAdapter {
        companion object{
            @JvmStatic
            @BindingAdapter("url")
            fun onLoadImage(view:ImageView,url:String?){
                Glide.with(view.context).load(url).into(view)
            }

            @JvmStatic
            @BindingAdapter("cUrl")
            fun onLoadCircleImage(view:CircleImageView,url:String?){
                if(url!=null)
                    Log.d("测试：URL：",url)
                Glide.with(view.context).load(url).into(view)
            }
        }
}