package com.kijlee.wb.fivemdogfood.ui.juhe

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.entity.juhe.CarBrand
import com.squareup.picasso.Picasso

class CarTypeAdapter   constructor(resLayout:Int,data:MutableList<CarBrand>):
    BaseQuickAdapter<CarBrand, BaseViewHolder>(resLayout,data) , LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: CarBrand) {
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