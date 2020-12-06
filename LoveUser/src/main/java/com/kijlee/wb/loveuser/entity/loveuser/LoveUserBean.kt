package com.kijlee.wb.loveuser.entity.loveuser

import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BmobFile

/**
 * 用于用户注册
 */
class LoveUserBean : BmobObject() {
    var numCode: String? = null//编号
    var managerId: String? = null//机构id
    var city: String? = null//位置
    var academic: String? = null//学历
    var income: String? = null//收入状况
    var hobby: String? = null//爱好
    var maritalStatus: String? = null//婚姻状况
    var profession: String? = null//职业及未来规划
    var family: String? = null//family
    var other: String? = null//期望的另一半
    var birthday: Long? = 0//出生日期
    var sex: String? = null//性别
    var vipLevel: String? = null//性别
    var attribute: String? = null//属性
    var occupation: String? = null//职业
    var degree: String? = null//学历
    var userImages: MutableList<BmobFile>? = ArrayList()
}