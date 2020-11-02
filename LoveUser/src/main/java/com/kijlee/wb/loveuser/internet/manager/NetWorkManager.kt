package com.kijlee.wb.loveuser.internet.manager

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.kijlee.wb.loveuser.base.App
import com.kijlee.wb.loveuser.internet.Api
import com.kijlee.wb.loveuser.internet.services.GetWebTimeServices
import com.kijlee.wb.loveuser.internet.services.JuheService
import com.kijlee.wb.loveuser.internet.services.PhpTestServices
import com.kijlee.wb.loveuser.internet.services.ShowApiServices
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络交互
 */

class NetWorkManager private constructor() {
    var mOkHttpClient: OkHttpClient
    var mRetrofit: Retrofit
    var getWebTimeServices: GetWebTimeServices
    var showApiServices: ShowApiServices
    var juheService: JuheService
    var phpTestServices: PhpTestServices

    init {
        val cookieJar =
            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.instance()))
        this.mOkHttpClient =
            RetrofitUrlManager.getInstance().with(OkHttpClient.Builder()) //RetrofitUrlManager 初始化
                .readTimeout(1000, TimeUnit.SECONDS)
                .connectTimeout(1000, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build()
        this.mRetrofit = Retrofit.Builder()
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())//使用Gson
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用rxjava
            .baseUrl(Api.default_URL)
            .build()
        this.getWebTimeServices = mRetrofit.create(GetWebTimeServices::class.java)
        this.showApiServices = mRetrofit.create(ShowApiServices::class.java)
        this.juheService = mRetrofit.create(JuheService::class.java)
        this.phpTestServices = mRetrofit.create(PhpTestServices::class.java)
    }

    private object Holder {
        val INSTANCE = NetWorkManager()
    }

    companion object {
        val instance: NetWorkManager by lazy { Holder.INSTANCE }
    }
}

