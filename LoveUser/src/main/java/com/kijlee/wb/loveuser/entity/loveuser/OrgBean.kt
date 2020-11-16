package com.kijlee.wb.loveuser.entity.loveuser

import cn.bmob.v3.BmobObject
import com.kijlee.wb.loveuser.annotations.NoArgOpenDataClass

/**
 * 机构数据
 */
@NoArgOpenDataClass
data class OrgBean constructor( var orgName: String?,//机构名称
                   var inviteCode: String?,//邀请码
                   var checkCode: String?,//校验码
                   var parentsObjectId: String?,//总店 父级机构id
                   var isOpen: Boolean?//是否在营业
) : BmobObject() {
    constructor():this(null,null,null,null,null)
}