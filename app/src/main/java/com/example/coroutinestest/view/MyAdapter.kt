package com.example.coroutinestest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinestest.R
import com.example.coroutinestest.databinding.RecyclerItemBinding
import com.example.coroutinestest.databinding.RecyclerTextItemBinding
import com.example.coroutinestest.model.data.Data

class MyAdapter(val type:String = "Text",list:ArrayList<Data> = ArrayList()):RecyclerView.Adapter<MyAdapter.Companion.viewHolder>() {
    var datas = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return if(type == "Text"){
            val dataBinding: RecyclerTextItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_text_item,parent,false)
            viewHolder(dataBinding.root)
        }else{
            val dataBinding: RecyclerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_item,parent,false)
            viewHolder(dataBinding.root)
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        if(type == "Text"){
            val dataBinding:RecyclerTextItemBinding = DataBindingUtil.getBinding(holder.itemView)!!
            dataBinding.data = datas[position]
            dataBinding.executePendingBindings()
        }else{
            val dataBinding:RecyclerItemBinding = DataBindingUtil.getBinding(holder.itemView)!!
            dataBinding.data = datas[position]
            dataBinding.executePendingBindings()
            val thumb = holder.itemView.findViewById<ImageView>(R.id.thumb)
            thumb.setOnClickListener { listener.apply { onClick(datas[position].t) } }
        }
    }

    fun insertDatas(newDatas:ArrayList<Data>){
        datas.addAll(newDatas)
        notifyDataSetChanged()
    }

    fun cleanDatas(){
        datas.clear()
        notifyDataSetChanged()
    }

    companion object{
        class viewHolder(view: View):RecyclerView.ViewHolder(view){
        }

    }

    interface TestListener{
        fun onClick(id:String)
    }

    private lateinit var listener:TestListener
    fun setTestListener(listener:TestListener){
        this.listener = listener
    }
}