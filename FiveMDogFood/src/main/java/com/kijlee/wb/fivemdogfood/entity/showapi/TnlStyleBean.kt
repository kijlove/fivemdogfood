package com.kijlee.wb.fivemdogfood.entity.showapi

import com.kijlee.wb.fivemdogfood.annotations.NoArgOpenDataClass
import java.io.Serializable

@NoArgOpenDataClass
data class TnlStyleBean<T>(
    val showapi_res_body: T,
    val showapi_res_code: Int,
    val showapi_res_error: String,
    val showapi_res_id: String
):Serializable

@NoArgOpenDataClass
data class ShowapiResBody(
    val allTypeList: List<String>,
    val num: Int,
    val ret_code: Int
):Serializable

@NoArgOpenDataClass
data class TnlModleBean(
    val avatarUrl: String?,
    val cardUrl: String?,
    val city: String?,
//    val height: Double?,
    val imgList: List<String>?,
    val link: String?,
    val realName: String,
    val totalFanNum: Int?,
    val totalFavorNum: Int?,
    val type: String,
    val userId: String?
//    val weight: Double?
):Serializable

@NoArgOpenDataClass
data class Pagebean(
    val allNum: Int,
    val allPages: Int,//所有页
    val contentlist: List<TnlModleBean>,
    val currentPage: Int,//当前页
    val maxResult: Int
):Serializable

@NoArgOpenDataClass
data class TnlStyleModleList(
    val pagebean: Pagebean,
    val ret_code: Int
):Serializable
