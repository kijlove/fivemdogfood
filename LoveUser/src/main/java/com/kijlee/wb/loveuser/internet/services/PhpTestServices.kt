package com.kijlee.wb.loveuser.internet.services

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PhpTestServices {

    @FormUrlEncoded
    @POST("adduser.php?")//文件审批
    fun adduser(@Field("value") value: String): Observable<ResponseBody>

}