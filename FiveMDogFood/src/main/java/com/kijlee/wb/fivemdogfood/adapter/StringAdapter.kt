package com.kijlee.wb.fivemdogfood.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.fivemdogfood.entity.MyApi

class StringAdapter  constructor(resLayout:Int,data:MutableList<String>):
    BaseQuickAdapter<String, BaseViewHolder>(resLayout,data) {
    override fun convert(holder: BaseViewHolder, item:String) {

        holder!!.setText(android.R.id.text1,item)
    }
}