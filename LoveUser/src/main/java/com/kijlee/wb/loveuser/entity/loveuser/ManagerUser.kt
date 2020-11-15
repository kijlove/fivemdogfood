package com.kijlee.wb.loveuser.entity.loveuser

import cn.bmob.v3.BmobUser
import com.kijlee.wb.loveuser.annotations.NoArgOpenDataClass

@NoArgOpenDataClass
data class ManagerUser(var role: Int? = 0,
                       var managerId: String? = null,//管理员id
                       var orgId: String? = null//机构id
) : BmobUser()