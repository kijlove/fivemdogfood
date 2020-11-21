package com.kijlee.wb.loveuser.ui.love.main.ui.org

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.entity.loveuser.LoveUserBean
import com.kijlee.wb.loveuser.entity.loveuser.OrgBean
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

/**
 * 机构列表适配器
 */
class OrgListAdapter  constructor(resLayout: Int, data: MutableList<OrgBean>) :
        BaseQuickAdapter<OrgBean, BaseViewHolder>(resLayout, data) {
    init {
        addChildClickViewIds(
                R.id.reItemLayout,
                R.id.cancleBtn,
                R.id.okBtn
        )
    }


    override fun convert(holder: BaseViewHolder, item: OrgBean) {
        holder!!.setText(R.id.orgNameText,item.orgName)
    }
}