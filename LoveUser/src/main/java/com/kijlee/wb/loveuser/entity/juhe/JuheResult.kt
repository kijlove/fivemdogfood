package com.kijlee.wb.loveuser.entity.juhe

import com.kijlee.wb.loveuser.annotations.NoArgOpenDataClass
import java.io.Serializable

@NoArgOpenDataClass
data class JuheResult<T>(
    val result: T,
    val error_code: Int,
    val reason: String
): Serializable

@NoArgOpenDataClass
data class CarBrand(
    val id: String,
    val brand_logo: String,
    val first_letter: String,
    val brand_name: String
): Serializable

//根据车品牌获取车型
@NoArgOpenDataClass
data class CarSeries(
    val brandid: String,
    val id: String,
    val levelid: String,
    val levelname: String,
    val name: String,
    val sname: String
): Serializable

//根据车系id查询旗下车型列表
@NoArgOpenDataClass
data class CarModels(
    val id: String,
    val name: String,
    val peizhi: String,
    val series_id: String,
    val year: String
): Serializable
