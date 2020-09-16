package com.kijlee.wb.fivemdogfood.entity.juhe

import com.kijlee.wb.fivemdogfood.annotations.NoArgOpenDataClass
import java.io.Serializable

@NoArgOpenDataClass
data class JuheResult<T>(
    val result: T,
    val error_code: Int,
    val reason: String
): Serializable

@NoArgOpenDataClass
data class CarType(
    val id: String,
    val brand_logo: String,
    val first_letter: String,
    val brand_name: String
): Serializable