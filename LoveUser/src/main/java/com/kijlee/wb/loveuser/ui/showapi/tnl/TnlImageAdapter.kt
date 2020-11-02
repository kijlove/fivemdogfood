package com.kijlee.wb.loveuser.ui.showapi.tnl

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.entity.showapi.TnlModleBean
import com.squareup.picasso.Picasso

class TnlImageAdapter  constructor(resLayout:Int,data:MutableList<TnlModleBean>):
    BaseQuickAdapter<TnlModleBean, BaseViewHolder>(resLayout,data) , LoadMoreModule{
    override fun convert(holder: BaseViewHolder, item: TnlModleBean) {
        holder!!.setText(R.id.imageName,item.realName)

        var imageView = holder!!.getView<ImageView>(R.id.imageView)
        if(item.avatarUrl!=null&&!item.avatarUrl!!.isEmpty()){
            Picasso.with(holder.itemView.context).load(item.avatarUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView)
        }
    }
}