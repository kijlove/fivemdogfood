package com.kijlee.wb.loveuser.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.mob.pushsdk.MobPushCustomMessage
import com.mob.pushsdk.MobPushNotifyMessage
import com.mob.pushsdk.MobPushReceiver
import com.vise.log.ViseLog

abstract class BaseActivity : AppCompatActivity(), MobPushReceiver {

    override fun onAliasCallback(
        context: Context?,
        alias: String?,
        operation: Int,
        errorCode: Int
    ) {
        //接收alias的增改删查操作
    }

    override fun onCustomMessageReceive(context: Context?, message: MobPushCustomMessage?) {
        //接收自定义消息(透传)
        ViseLog.e("接收自定义消息=====================::" + message.toString())
    }

    override fun onNotifyMessageReceive(context: Context?, message: MobPushNotifyMessage?) {
        //接收通知消
        ViseLog.e("接收通知消=====================:" + message.toString())
    }

    override fun onTagsCallback(
        context: Context?,
        array: Array<out String>?,
        operation: Int,
        errorCode: Int
    ) {
        //接收tags的增改删查操作
    }

    override fun onNotifyMessageOpenedReceive(context: Context?, message: MobPushNotifyMessage?) {
        //接收通知消息被点击事件
        ViseLog.e("接收通知消息被点击事件=====================:" + message.toString())
    }

}