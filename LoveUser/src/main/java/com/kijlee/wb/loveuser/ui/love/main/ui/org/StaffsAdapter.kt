package com.kijlee.wb.loveuser.ui.love.main.ui.org

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser

/**
 * 员工列表
 */
class StaffsAdapter  constructor(resLayout: Int, data: MutableList<ManagerUser>) :
    BaseQuickAdapter<ManagerUser, BaseViewHolder>(resLayout, data) {
    init {
        addChildClickViewIds(
            R.id.text1
        )
    }


    override fun convert(holder: BaseViewHolder, item: ManagerUser) {
        holder!!.setText(R.id.text1,item.realName+item.roleCode+item.username)
    }
}