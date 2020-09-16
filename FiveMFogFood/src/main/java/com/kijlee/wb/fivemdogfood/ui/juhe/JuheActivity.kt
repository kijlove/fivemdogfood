package com.kijlee.wb.fivemdogfood.ui.juhe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.base.BaseActivity
import com.kijlee.wb.fivemdogfood.entity.juhe.CarType
import com.kijlee.wb.fivemdogfood.entity.juhe.JuheResult
import com.kijlee.wb.fivemdogfood.entity.showapi.ShowapiResBody
import com.kijlee.wb.fivemdogfood.entity.showapi.TnlModleBean
import com.kijlee.wb.fivemdogfood.entity.showapi.TnlStyleBean
import com.kijlee.wb.fivemdogfood.internet.Api
import com.kijlee.wb.fivemdogfood.internet.manager.NetWorkManager
import com.kijlee.wb.fivemdogfood.ui.showapi.tnl.TnlActivity
import com.kijlee.wb.fivemdogfood.ui.showapi.tnl.TnlImageAdapter
import com.vise.log.ViseLog
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_recyclerview.*
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.ResponseBody

class JuheActivity : BaseActivity() {
    var adapter: CarTypeAdapter? = null
    val imgList: MutableList<CarType>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_recyclerview)
        adapter = CarTypeAdapter(R.layout.layout_image_horizontal, imgList!!)
        recyclerView.adapter = adapter
        ViseLog.e("聚合数据==============" )
        brand()
    }

    //获取服务器时间对比本地时间是否超过或等于15天
    fun brand() {
        //设置登录的url
        RetrofitUrlManager.getInstance().removeGlobalDomain()//移除全据url
        RetrofitUrlManager.getInstance().setGlobalDomain(Api.juhe_URL)
        val httpUrl2 = RetrofitUrlManager.getInstance().getGlobalDomain()
        ViseLog.e("聚合数据==============" + httpUrl2.toString())

        if (httpUrl2 == null || httpUrl2.toString() != Api.juhe_URL) { //可以在 App 运行时随意切换某个接口的 BaseUrl
            ViseLog.e("聚合数据==============" + httpUrl2.toString())
            RetrofitUrlManager.getInstance().putDomain(Api.juhe, Api.juhe_URL)
        }
//        如果您已经确定了最终的 BaseUrl ,不需要再动态切换 BaseUrl, 请
//        RetrofitUrlManager.getInstance().setRun(false)
        NetWorkManager.instance.juheService!!.brand(
            "a05788cc2dd6ac676d690062bf9489b3"
        )
            //设置事件触发在非主线程
            .subscribeOn(Schedulers.io())
            //设置事件接受在UI线程以达到UI显示的目的
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<JuheResult<MutableList<CarType>>> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: JuheResult<MutableList<CarType>>) {
                    adapter!!.setList(response.result)
                }

                override fun onError(e: Throwable) {
                    ViseLog.e("onError==============" + e.message)
                }
            })
    }
}