package com.kijlee.wb.loveuser.entity.loveuser

import cn.bmob.v3.BmobObject

/**
 * 员工角色
 */
class StaffBean : BmobObject() {

    var oid: String? = null//机构id
    var userName: String? = null//姓名
    var role: String? = null//角色
    var moblePhone: String? = null//手机号

}