package com.kijlee.wb.fivemdogfood.internet.services

import com.kijlee.wb.fivemdogfood.entity.juhe.CarType
import com.kijlee.wb.fivemdogfood.entity.juhe.JuheResult
import com.kijlee.wb.fivemdogfood.entity.showapi.ShowapiResBody
import com.kijlee.wb.fivemdogfood.entity.showapi.TnlStyleBean
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface JuheService {
//    http://apis.juhe.cn/cxdq/brand?key=a05788cc2dd6ac676d690062bf9489b3
    @GET("cxdq/brand?")//获取服务器时间
    fun brand(
        @Query(value = "key") key: String?
    ): Observable<JuheResult<MutableList<CarType>>>
// http://apis.juhe.cn/cxdq/series?key=a05788cc2dd6ac676d690062bf9489b3&brandid=1&levelid=5
    @GET("cxdq/series?")//获取服务器时间
    fun series(
        @Query(value = "key") key: String?
    ): Observable<JuheResult<MutableList<CarType>>>
}