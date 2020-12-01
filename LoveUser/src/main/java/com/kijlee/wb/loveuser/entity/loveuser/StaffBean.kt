package com.kijlee.wb.loveuser.entity.loveuser

import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobUser
import com.kijlee.wb.loveuser.annotations.NoArgOpenDataClass

/**
 * 员工角色
 */

@NoArgOpenDataClass
data class StaffBean (var role: Int? = 0,
var roleCode: String? = null,//角色code
var realName: String? = null,//姓名
var orgId: String? = null//机构id
) : BmobUser()