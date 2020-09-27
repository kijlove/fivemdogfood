package com.kijlee.wb.yszx.ui.showapi

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.yszx.entity.MyApi

class MyApiAdapter constructor(resLayout:Int,data:MutableList<MyApi>):BaseQuickAdapter<MyApi,BaseViewHolder>(resLayout,data) {
    override fun convert(holder: BaseViewHolder, item: MyApi) {

        holder!!.setText(android.R.id.text1,holder.itemView.context.getString(item.nameRec))
    }
}