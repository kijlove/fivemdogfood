package com.kijlee.wb.loveuser.entity.loveuser

import cn.bmob.v3.BmobUser
import com.kijlee.wb.loveuser.annotations.NoArgOpenDataClass

@NoArgOpenDataClass
data class ManagerUser(var role: Int? = 0,
                       var roleCode: RoleCode? = null,//角色code
                       var realName: String? = null,//姓名
                       var orgId: String? = null//机构id
) : BmobUser()

enum class RoleCode{
    BOSS, EDITER
}
