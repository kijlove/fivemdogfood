package com.kijlee.wb.loveuser.entity.loveuser

import java.io.Serializable

/**
 * 城市列表
 */
data class CityBean(
    val cityList: List<CityBean>,
    val id: String,
    val name: String,
    val pinYin: String
):Serializable


