package com.kijlee.wb.loveuser.entity.loveuser

import cn.bmob.v3.BmobUser
import com.kijlee.wb.loveuser.annotations.NoArgOpenDataClass

@NoArgOpenDataClass
data class ManagerUser(var role: Int? = 0,
                       var roleCode: String? = null,//角色code
                       var orgId: String? = null//机构id
) : BmobUser()