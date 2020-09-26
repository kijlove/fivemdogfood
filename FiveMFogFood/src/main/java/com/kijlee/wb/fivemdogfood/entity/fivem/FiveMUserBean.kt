package com.kijlee.wb.fivemdogfood.entity.fivem

import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile

/**
 * 用于用户注册
 */
class FiveMUserBean : BmobUser() {
    var numCode:String? = null//编号
    var local:String? = null//位置
    var age:Int? = 0//年龄
    var sex:String? = null//性别
    var attribute:String? = null//属性
    var occupation:String? = null//职业
    var objective:String? = null//目的
    var evaluate:String? = null//评价
    var degree:String? = null//程度
    var userImages:MutableList<BmobFile>? = ArrayList()
}