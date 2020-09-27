package com.kijlee.wb.yszx.entity.phptest

import com.kijlee.wb.yszx.annotations.NoArgOpenDataClass
import java.io.Serializable


//根据车系id查询旗下车型列表
@NoArgOpenDataClass
data class CarUserBean(
    val userName: String,
    val userPhone: String,
    val userCarId: Int
): Serializable