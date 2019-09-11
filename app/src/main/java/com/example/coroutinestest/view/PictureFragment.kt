package com.example.coroutinestest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinestest.R
import com.example.coroutinestest.databinding.FragmentLayoutBinding
import com.example.coroutinestest.model.data.Data
import com.example.coroutinestest.view_model.MyViewModel
import com.example.coroutinestest.view_model.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_layout.*

class PictureFragment:BaseFragment() {
    private val viewModel by lazy { ViewModelProviders.of(this,
        ViewModelFactory(activity?.application!!)
    )[MyViewModel::class.java] }
    private lateinit var dataBinding: FragmentLayoutBinding
    private lateinit var adapter: MyAdapter
    override fun loadData() {
        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = manager
        adapter = MyAdapter("Other")
        recyclerView.adapter = adapter
        viewModel.onLoadPictureData()
        viewModel.newDatas.observe(this,
            Observer<ArrayList<Data>> {
                refresh.finishRefresh()
                refresh.finishLoadMore()
                adapter.insertDatas(it)
            })
        viewModel.error.observe(this, Observer {
            Toast.makeText(activity,it.message, Toast.LENGTH_LONG).show()
        })
        refresh.setOnRefreshListener{
            adapter.cleanDatas()
            viewModel.onLoadPictureData()
        }
        refresh.setOnLoadMoreListener {
            viewModel.onLoadPictureData()
        }
        adapter.setTestListener(object :MyAdapter.TestListener{
            override fun onClick(id: String) {
                Log.d("测试：","TEST_ON_CLICKED")
                viewModel.onLoveByTest(id)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_layout,container,false)
        return dataBinding.root
    }
}