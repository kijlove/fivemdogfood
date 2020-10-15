package com.kijlee.wb.fivemdogfood.ui.juhe

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.fivemdogfood.entity.juhe.CarModels
import com.kijlee.wb.fivemdogfood.entity.juhe.CarSeries

class CarModelsAdapter  constructor(resLayout:Int,data:MutableList<CarModels>):
    BaseQuickAdapter<CarModels, BaseViewHolder>(resLayout,data) , LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: CarModels) {
        holder!!.setText(android.R.id.text1,item.name)
    }
}