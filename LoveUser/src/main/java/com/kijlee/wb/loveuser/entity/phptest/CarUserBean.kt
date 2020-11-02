package com.kijlee.wb.loveuser.entity.phptest

import com.kijlee.wb.loveuser.annotations.NoArgOpenDataClass
import java.io.Serializable


//根据车系id查询旗下车型列表
@NoArgOpenDataClass
data class CarUserBean(
    val userName: String,
    val userPhone: String,
    val userCarId: Int
): Serializable