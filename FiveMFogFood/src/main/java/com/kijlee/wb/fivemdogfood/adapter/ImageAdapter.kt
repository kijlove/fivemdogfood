package com.kijlee.wb.fivemdogfood.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.entity.showapi.TnlModleBean
import com.squareup.picasso.Picasso

class ImageAdapter  constructor(resLayout:Int,data:MutableList<String>):
    BaseQuickAdapter<String, BaseViewHolder>(resLayout,data) {
    override fun convert(holder: BaseViewHolder, item:String) {
        var imageView = holder!!.getView<ImageView>(R.id.imageView)
        holder!!.setGone(R.id.imageName,true)
        if(item!=null&&!item.isEmpty()){
            Picasso.with(holder.itemView.context).load(item)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView)
        }
    }
}