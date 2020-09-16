package com.kijlee.wb.fivemdogfood.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.base.BaseActivity
import com.kijlee.wb.fivemdogfood.entity.MyApi
import com.kijlee.wb.fivemdogfood.entity.showapi.ShowapiResBody
import com.kijlee.wb.fivemdogfood.entity.showapi.TnlStyleBean
import com.kijlee.wb.fivemdogfood.internet.Api
import com.kijlee.wb.fivemdogfood.internet.manager.NetWorkManager
import com.kijlee.wb.fivemdogfood.ui.showapi.MyApiAdapter
import com.kijlee.wb.fivemdogfood.ui.showapi.tnl.TnlActivity
import com.mob.pushsdk.MobPush
import com.vise.log.ViseLog
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_recyclerview.*
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class MainActivity : BaseActivity() {

    var adapter: MyApiAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobPush.getRegistrationId { s->
            ViseLog.e("getRegistrationId=====================::" + s.toString())
        }
        MobPush.addPushReceiver(this)
        setContentView(R.layout.layout_recyclerview)
        adapter = MyApiAdapter(android.R.layout.simple_list_item_1, MyApi.myApiList())
        recyclerView.adapter = adapter
        adapter!!.setOnItemClickListener { adapter, view, position ->
            ViseLog.e("风格==============")
            var intent = Intent(baseContext,(adapter.data[position] as MyApi).activity!!)
            startActivity( intent)
        }
    }



    //获取服务器时间对比本地时间是否超过或等于15天
    fun getWebTime() {
        NetWorkManager.instance.getWebTimeServices!!.getWebTime()
            //设置事件触发在非主线程
            .subscribeOn(Schedulers.io())
            //设置事件接受在UI线程以达到UI显示的目的
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseBody> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseBody) {
                    var docMent: Document = Jsoup.parse(response.string())
                    var body: Elements = docMent.getElementsByTag("body")//获取body标签的元素
                    var element: Element = body.get(0);//因为body是一个集合类型，所以需要取集合里的第一个元素
                    ViseLog.e("服务器时间=====" + element.text())
                }

                override fun onError(e: Throwable) {}
            })
    }
}