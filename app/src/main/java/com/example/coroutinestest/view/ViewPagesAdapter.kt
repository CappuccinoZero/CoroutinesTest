package com.example.coroutinestest.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagesAdapter(fragmentManager: FragmentManager,val fragments:ArrayList<Fragment>):FragmentStatePagerAdapter(fragmentManager){
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if(position == 0 ) "图片" else "文字"
    }
}