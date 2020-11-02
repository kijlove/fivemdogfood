package com.kijlee.wb.loveuser.entity.loveuser

import cn.bmob.v3.BmobUser

class ManagerUser : BmobUser() {
    var role: Int? = 0
    var managerId: String? = null//管理员id
}