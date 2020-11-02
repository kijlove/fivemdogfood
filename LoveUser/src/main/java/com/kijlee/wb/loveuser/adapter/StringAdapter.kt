package com.kijlee.wb.loveuser.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class StringAdapter  constructor(resLayout:Int,data:MutableList<String>):
    BaseQuickAdapter<String, BaseViewHolder>(resLayout,data) {
    override fun convert(holder: BaseViewHolder, item:String) {

        holder!!.setText(android.R.id.text1,item)
    }
}