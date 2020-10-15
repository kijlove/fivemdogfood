package com.kijlee.wb.fivemdogfood.internet.services

import com.kijlee.wb.fivemdogfood.entity.juhe.CarSeries
import com.kijlee.wb.fivemdogfood.entity.juhe.CarBrand
import com.kijlee.wb.fivemdogfood.entity.juhe.CarModels
import com.kijlee.wb.fivemdogfood.entity.juhe.JuheResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface JuheService {
//    http://apis.juhe.cn/cxdq/brand?key=a05788cc2dd6ac676d690062bf9489b3
    @GET("cxdq/brand?")//获取服务器时间
    fun brand(
        @Query(value = "key") key: String?
    ): Observable<JuheResult<MutableList<CarBrand>>>
// http://apis.juhe.cn/cxdq/series?key=a05788cc2dd6ac676d690062bf9489b3&brandid=1&levelid=5
    @GET("cxdq/series?")//
    fun series(
        @Query(value = "key") key: String?,
        @Query(value = "brandid") brandid: Int?,
        @Query(value = "levelid") levelid: Int?
    ): Observable<JuheResult<MutableList<CarSeries>>>
    @GET("cxdq/models?")//
    fun models(
        @Query(value = "key") key: String?,
        @Query(value = "series_id") brandid: Int?
    ): Observable<JuheResult<MutableList<CarModels>>>
}