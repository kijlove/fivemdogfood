package com.kijlee.wb.yszx.internet


/**
 *
 */
 interface Api {
    companion object {
        val default: String get() = "default"
        val juhe: String get() = "juhe"
        val showapi: String get() = "showapi"
        val phpTest: String get() = "phpTest"
        val default_URL: String get() = "http://dvm02.btransm.com/"//默认
        val juhe_URL: String get() = "http://apis.juhe.cn/"//聚合api
        val showapi_URL: String get() = "http://route.showapi.com/"//万维易源
        val Update_Download_Url: String get() = "http://"
        val phpTest_URL: String get() = "http://39.98.160.25/"
    }
}