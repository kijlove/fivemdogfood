package com.kijlee.wb.fivemdogfood.ui.juhe

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.entity.juhe.CarType
import com.kijlee.wb.fivemdogfood.entity.showapi.TnlModleBean
import com.squareup.picasso.Picasso

class CarTypeAdapter   constructor(resLayout:Int,data:MutableList<CarType>):
    BaseQuickAdapter<CarType, BaseViewHolder>(resLayout,data) , LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: CarType) {
        holder!!.setText(R.id.imageName,item.brand_name)

        var imageView = holder!!.getView<ImageView>(R.id.imageView)
        if(item.brand_logo!=null&&!item.brand_logo!!.isEmpty()){
            Picasso.with(holder.itemView.context).load(item.brand_logo)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView)
        }
    }
}