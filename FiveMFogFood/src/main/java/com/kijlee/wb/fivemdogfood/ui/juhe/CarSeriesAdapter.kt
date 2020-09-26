package com.kijlee.wb.fivemdogfood.ui.juhe

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.fivemdogfood.entity.juhe.CarSeries

class CarSeriesAdapter   constructor(resLayout:Int,data:MutableList<CarSeries>):
    BaseQuickAdapter<CarSeries, BaseViewHolder>(resLayout,data) , LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: CarSeries) {
        holder!!.setText(android.R.id.text1,item.name)
    }
}