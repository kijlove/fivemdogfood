package com.kijlee.wb.yszx.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kijlee.wb.yszx.R
import com.kijlee.wb.yszx.base.BaseActivity
import com.kijlee.wb.yszx.entity.MyApi
import com.kijlee.wb.yszx.entity.showapi.ShowapiResBody
import com.kijlee.wb.yszx.entity.showapi.TnlStyleBean
import com.kijlee.wb.yszx.internet.Api
import com.kijlee.wb.yszx.internet.manager.NetWorkManager
import com.kijlee.wb.yszx.ui.showapi.MyApiAdapter
import com.kijlee.wb.yszx.ui.showapi.tnl.TnlActivity
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
    //检查必要权限
    var arrayPermission: Array<String> = arrayOf(
//        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA)
    var mPermissionList: MutableList<String> = ArrayList()//权限list
    //检查权限
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 8

    var adapter: MyApiAdapter? = null
    //请求权限sign_norm_activity
    fun requestPermissions() {

        for (i in arrayPermission) {
            if (ContextCompat.checkSelfPermission(baseContext, i) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(i)
            }
        }
        if (mPermissionList.size > 0) {//还有没获取到的权限就继续请求
            ActivityCompat.requestPermissions(this,
                arrayPermission,
                PERMISSIONS_REQUEST_READ_CONTACTS)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobPush.getRegistrationId { s->
            ViseLog.e("getRegistrationId=====================::" + s.toString())
        }
        MobPush.addPushReceiver(this)
        requestPermissions()
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