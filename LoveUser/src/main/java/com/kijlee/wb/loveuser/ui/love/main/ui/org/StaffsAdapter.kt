package com.kijlee.wb.loveuser.ui.love.main.ui.org

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.entity.loveuser.OrgBean
import com.kijlee.wb.loveuser.entity.loveuser.StaffBean

/**
 * 员工列表
 */
class StaffsAdapter  constructor(resLayout: Int, data: MutableList<StaffBean>) :
    BaseQuickAdapter<StaffBean, BaseViewHolder>(resLayout, data) {
    init {
        addChildClickViewIds(
            R.id.text1
        )
    }


    override fun convert(holder: BaseViewHolder, item: StaffBean) {
        holder!!.setText(R.id.text1,item.userName)
    }
}