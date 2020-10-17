package com.kijlee.wb.fivemdogfood.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.entity.fivem.ImageViewEditBean
import com.squareup.picasso.Picasso
import com.vise.log.ViseLog

/**
 * 编辑图片适配器
 */
class EditImageListAdapter constructor(resLayoutId: Int, data: MutableList<ImageViewEditBean>) :
        BaseQuickAdapter<ImageViewEditBean, BaseViewHolder>(resLayoutId, data) {
    init {
        addChildClickViewIds(
            R.id.delImageView
        )
    }
    override fun convert(helper: BaseViewHolder, item: ImageViewEditBean) {
        //新增隐藏掉删除按钮  如果不是就显示删除按钮用于删除
        helper!!.setGone(R.id.delImageView, item!!.isShowDelIcon)
        var imageView: ImageView = helper!!.getView(R.id.imageView)
        if(item!!.isAddIcon) {//如果不是新增 就用于显示图片
            imageView.setImageResource(R.drawable.ic_baseline_photo_camera_24)
        }else{

            Picasso.with(helper!!.itemView.context)
                    .load(if (item!!.isLocal) {
                        "file://" + item!!.imagePath
                    }   else {
                        item!!.imagePath
                    })
                    .placeholder(R.drawable.ic_baseline_photo_camera_24)
                    .error(R.drawable.ic_baseline_photo_camera_24)
                    .into(imageView)
        }
    }
}