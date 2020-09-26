package com.kijlee.wb.fivemdogfood.ui.fivem.main.ui.home

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.entity.fivem.FiveMUserBean
import com.squareup.picasso.Picasso

class FiveMUserAdapter constructor(resLayout: Int, data: MutableList<FiveMUserBean>) :
    BaseQuickAdapter<FiveMUserBean, BaseViewHolder>(resLayout, data) {
init {
    addChildClickViewIds(
        R.id.image1,
        R.id.image2,
        R.id.image3,
        R.id.shareImage
    )
}
    var array: Array<String> = arrayOf(

        "https://img.tupianzj.com/uploads/allimg/202009/9999/984c2cbc21.jpg",
        "https://img.tupianzj.com/uploads/allimg/202008/9999/83c2bbb7f2.jpg",
        "https://img.tupianzj.com/uploads/allimg/202008/9999/96fd3ec312.jpg"
    )

    override fun convert(holder: BaseViewHolder, item: FiveMUserBean) {
        holder!!.setText(
            R.id.numCode, if (item.numCode == null) {
                "编号:"
            } else {
                "编号:" + item.numCode
            }
        )
            .setText(
                R.id.occupation, if (item.occupation == null) {
                    "职业:"
                } else {
                    "职业:" + item.occupation
                }
            )
            .setText(
                R.id.sex, if (item.sex == null) {
                    "性别:"
                } else {
                    "性别:" + item.sex
                }
            )
            .setText(
                R.id.age, if (item.age == null) {
                    "年龄:"
                } else {
                    "年龄:" + item.age.toString()
                }
            )
            .setText(
                R.id.degree, if (item.degree == null) {
                    "程度:"
                } else {
                    "程度:" + item.degree
                }
            )
            .setText(
                R.id.attribute, if (item.attribute == null) {
                    "属性:"
                } else {
                    "属性:" + item.attribute
                }
            )
            .setText(
                R.id.evaluate, if (item.evaluate == null) {
                    "自评:"
                } else {
                    "自评:" + item.evaluate
                }
            )

        var imageView1 = holder!!.getView<ImageView>(R.id.image1)
        var imageView2 = holder!!.getView<ImageView>(R.id.image2)
        var imageView3 = holder!!.getView<ImageView>(R.id.image3)

        Picasso.with(holder.itemView.context).load(array[0])
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(imageView1)
        Picasso.with(holder.itemView.context).load(array[1])
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(imageView2)
        Picasso.with(holder.itemView.context).load(array[2])
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(imageView3)
    }
}