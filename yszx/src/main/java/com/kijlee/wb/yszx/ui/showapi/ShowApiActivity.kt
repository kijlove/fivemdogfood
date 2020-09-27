package com.kijlee.wb.yszx.ui.showapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kijlee.wb.yszx.R
import com.kijlee.wb.yszx.base.BaseActivity
import com.kijlee.wb.yszx.entity.MyApi
import com.kijlee.wb.yszx.entity.showapi.ShowapiResBody
import com.kijlee.wb.yszx.entity.showapi.TnlStyleBean
import com.kijlee.wb.yszx.internet.Api
import com.kijlee.wb.yszx.internet.manager.NetWorkManager
import com.kijlee.wb.yszx.ui.showapi.tnl.TnlActivity
import com.vise.log.ViseLog
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_defaut_text.view.*
import kotlinx.android.synthetic.main.layout_recyclerview.*
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/***
 * 万维易源api
 */
class ShowApiActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_norm)
        allTypeList()
    }

    //获取服务器时间对比本地时间是否超过或等于15天
    fun allTypeList() {
        //设置登录的url
        RetrofitUrlManager.getInstance().removeGlobalDomain()//移除全据url
        RetrofitUrlManager.getInstance().setGlobalDomain(Api.showapi_URL)
        val httpUrl2 = RetrofitUrlManager.getInstance().getGlobalDomain()
        ViseLog.e("风格==============" + httpUrl2.toString())

        if (httpUrl2 == null || httpUrl2.toString() != Api.showapi_URL) { //可以在 App 运行时随意切换某个接口的 BaseUrl
            ViseLog.e("风格==============" + httpUrl2.toString())
            RetrofitUrlManager.getInstance().putDomain(Api.showapi, Api.showapi_URL)
        }
//        如果您已经确定了最终的 BaseUrl ,不需要再动态切换 BaseUrl, 请
//        RetrofitUrlManager.getInstance().setRun(false)
        NetWorkManager.instance.showApiServices!!.allTypeList(
            "b2f3a718a48d4e5abb15d159843eb086",
            "3146"
        )
            //设置事件触发在非主线程
            .subscribeOn(Schedulers.io())
            //设置事件接受在UI线程以达到UI显示的目的
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<TnlStyleBean<ShowapiResBody>> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: TnlStyleBean<ShowapiResBody>) {
                    //跳转到下一级
                    var intent = Intent(baseContext, TnlActivity::class.java)
                    intent.putExtra("TnlStyleBean", response)
                    startActivity(intent)
                }

                override fun onError(e: Throwable) {
                    ViseLog.e("onError==============" + e.message)
                }
            })
    }
}