package com.kijlee.wb.loveuser.ui.love.main.ui.home

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.entity.loveuser.LoveUserBean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fg_add_fivem_user.*
import java.text.SimpleDateFormat
import java.util.*

class FiveMUserAdapter constructor(resLayout: Int, data: MutableList<LoveUserBean>) :
    BaseQuickAdapter<LoveUserBean, BaseViewHolder>(resLayout, data) {
    init {
        addChildClickViewIds(
            R.id.image1,
            R.id.shareImage
        )
    }

    var array: Array<String> = arrayOf(

        "https://img.tupianzj.com/uploads/allimg/202009/9999/984c2cbc21.jpg",
        "https://img.tupianzj.com/uploads/allimg/202008/9999/83c2bbb7f2.jpg",
        "https://img.tupianzj.com/uploads/allimg/202008/9999/96fd3ec312.jpg"
    )

    override fun convert(holder: BaseViewHolder, item: LoveUserBean) {
        holder!!.setText(
            R.id.numCode, if (item.numCode == null) {
                "编号:"
            } else {
                "编号:" + item.numCode
            }
            )
            .setText(
                R.id.birthdayText, if (item.birthday == null) {
                    "出生年月:"
                } else {
                    "出生年月:" +  SimpleDateFormat("yyyy-MM-dd").format(Date(item.birthday!!))
                }
            )
            .setText(
                R.id.maritalStatus, if (item.maritalStatus == null) {
                    "婚姻状况:"
                } else {
                    "婚姻状况:" + item.maritalStatus
                }
            )
            .setText(
                R.id.vipLevel, if (item.vipLevel == null) {
                    "会员等级:"
                } else {
                    "会员等级:" + item.vipLevel
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
                R.id.academic, if (item.academic == null) {
                    "学历:"
                } else {
                    "学历:" + item.academic
                }
            )
            .setText(
                R.id.attribute, if (item.attribute == null) {
                    "属性:"
                } else {
                    "属性:" + item.attribute
                }
            )


        var imageView1 = holder!!.getView<ImageView>(R.id.image1)
        for (i in 0..item.userImages!!.size - 1) {
            when (i) {
                0 -> {
                    Picasso.with(holder.itemView.context).load(item.userImages!![0].url)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(imageView1)
                }
            }
        }
    }
}