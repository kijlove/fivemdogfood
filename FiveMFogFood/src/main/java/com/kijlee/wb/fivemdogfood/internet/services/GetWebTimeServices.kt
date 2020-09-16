package com.kijlee.wb.fivemdogfood.internet.services


import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface GetWebTimeServices {
    @GET("a.jsp")//获取服务器时间
    fun getWebTime(): Observable<ResponseBody>

}