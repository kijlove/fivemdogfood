package com.kijlee.wb.loveuser.entity.loveuser

import com.kijlee.wb.loveuser.annotations.NoArgOpenDataClass
import java.io.Serializable

/**
 * 城市列表
 */
@NoArgOpenDataClass
data class CityBean(
    val cityList: List<CityBean>,
    val id: String,
    val name: String,
    val pinYin: String
):Serializable


