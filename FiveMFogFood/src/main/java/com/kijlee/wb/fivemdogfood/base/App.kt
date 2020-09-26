package com.kijlee.wb.fivemdogfood.base

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import android.util.Log
import androidx.multidex.MultiDex
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobConfig
import com.kijlee.wb.fivemdogfood.internet.Api
import com.vise.log.ViseLog
import com.vise.log.inner.LogcatTree
import me.jessyan.retrofiturlmanager.RetrofitUrlManager


/**
 * 外包项目入口
 */
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this);
        ViseLog.getLogConfig()
            .configAllowLog(true) //是否输出日志
            .configShowBorders(true) //是否排版显示
            .configTagPrefix("ViseLog") //设置标签前缀
            .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}") //个性化设置标签，默认显示包名
            .configLevel(Log.VERBOSE) //设置日志最小输出级别，默认Log.VERBOSE

        ViseLog.plant(LogcatTree()) //添加打印日志信息到Logcat的树
        RetrofitUrlManager.getInstance().putDomain(Api.juhe, Api.juhe_URL)//聚合api
        RetrofitUrlManager.getInstance().putDomain(Api.showapi, Api.showapi_URL)//万维易源
        //防止多进程注册多次  可以在MainActivity或者其他页面注册MobPushReceiver
        //防止多进程注册多次  可以在MainActivity或者其他页面注册MobPushReceiver
        val processName = getProcessName(this)
        if (packageName == processName) {
        }


        //设置BmobConfig
        //第一：默认初始化
//        Bmob.initialize(this, "c4ae4d1367f906f671bf4030c4b02bc1");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        var config:BmobConfig = BmobConfig.Builder(this)
        //设置appkey
        .setApplicationId("c4ae4d1367f906f671bf4030c4b02bc1")
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(30)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);
    }


    private fun getProcessName(context: Context): String? {
        val am =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps =
            am.runningAppProcesses ?: return null
        for (proInfo in runningApps) {
            if (proInfo.pid == Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName
                }
            }
        }
        return null
    }


    companion object {
        private var instance: Application? = null
        fun instance() = instance!!
    }
}