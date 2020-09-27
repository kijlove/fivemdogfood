package com.kijlee.wb.yszx.entity.fivem

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


