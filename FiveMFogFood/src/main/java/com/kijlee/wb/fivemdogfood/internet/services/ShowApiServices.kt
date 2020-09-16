package com.kijlee.wb.fivemdogfood.internet.services

import com.kijlee.wb.fivemdogfood.entity.showapi.ShowapiResBody
import com.kijlee.wb.fivemdogfood.entity.showapi.TnlStyleBean
import com.kijlee.wb.fivemdogfood.entity.showapi.TnlStyleModleList
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowApiServices {
//    http://route.showapi.com/126-1?showapi_appid=3146&showapi_sign=b2f3a718a48d4e5abb15d159843eb086
    @GET("126-1?")//获取服务器时间
    fun allTypeList(
        @Query(value = "showapi_sign") showapi_sign: String?,
        @Query(value = "showapi_appid") showapi_appid: String?
    ): Observable<TnlStyleBean<ShowapiResBody>>
    //http://route.showapi.com/126-2?type=%E6%AC%A7%E7%BE%8E&order=&page=&showapi_appid=3146&showapi_sign=b2f3a718a48d4e5abb15d159843eb086
    @GET("126-2?")//获取服务器时间
    fun contentlist(
        @Query(value = "showapi_sign") showapi_sign: String?,
        @Query(value = "showapi_appid") showapi_appid: String?,
        @Query(value = "type") type: String?,
        @Query(value = "order") order: String?,//排序方式
        @Query(value = "page") page: String?
    ): Observable<TnlStyleBean<TnlStyleModleList>>

}