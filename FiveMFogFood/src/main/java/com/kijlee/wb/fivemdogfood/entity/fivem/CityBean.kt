package com.kijlee.wb.fivemdogfood.entity.fivem

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


