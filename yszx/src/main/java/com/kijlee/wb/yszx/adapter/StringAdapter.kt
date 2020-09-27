package com.kijlee.wb.yszx.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.yszx.entity.MyApi

class StringAdapter  constructor(resLayout:Int,data:MutableList<String>):
    BaseQuickAdapter<String, BaseViewHolder>(resLayout,data) {
    override fun convert(holder: BaseViewHolder, item:String) {

        holder!!.setText(android.R.id.text1,item)
    }
}