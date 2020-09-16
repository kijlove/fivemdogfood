package com.kijlee.wb.fivemdogfood.base

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Message
import android.os.Process
import android.util.Log
import androidx.multidex.MultiDex
import com.kijlee.wb.fivemdogfood.internet.Api
import com.mob.pushsdk.MobPush
import com.mob.pushsdk.MobPushCustomMessage
import com.mob.pushsdk.MobPushNotifyMessage
import com.mob.pushsdk.MobPushReceiver
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