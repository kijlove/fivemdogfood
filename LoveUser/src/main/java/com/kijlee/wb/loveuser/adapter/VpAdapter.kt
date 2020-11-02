package com.kijlee.wb.loveuser.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * Created by kij13 on 2017/9/4.
 * viewpage适配器
 */


class VpAdapter constructor(fm: FragmentManager, data: MutableList<Fragment>, list_Title: Array<String>) : FragmentPagerAdapter(fm) {
    var data: MutableList<Fragment>
    var list_Title: Array<String>

    init {
        this.data = data
        this.list_Title = list_Title
    }

    override fun getItem(position: Int): Fragment {
        return data[position]
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return list_Title[position % list_Title.size]
    }

}