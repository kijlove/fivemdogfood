package com.kijlee.wb.loveuser.entity.loveuser

import cn.bmob.v3.BmobObject
import com.kijlee.wb.loveuser.annotations.NoArgOpenDataClass

/**
 * 机构数据
 */
@NoArgOpenDataClass
data class OrgBean(val orgName: String,//机构名称
                   val inviteCode: String?,//邀请码
                   val parentsObjectId: String?,//总店 父级机构id
                   val isOpen: Boolean//是否在营业
) : BmobObject()