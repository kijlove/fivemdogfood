package com.kijlee.wb.yszx.ui.phptest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.kijlee.wb.yszx.R
import com.kijlee.wb.yszx.entity.juhe.CarBrand
import com.kijlee.wb.yszx.entity.juhe.JuheResult
import com.kijlee.wb.yszx.entity.phptest.CarUserBean
import com.kijlee.wb.yszx.internet.Api
import com.kijlee.wb.yszx.internet.manager.NetWorkManager
import com.vise.log.ViseLog
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fg_php_adduser.*
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.ResponseBody

class Fg_PhpTest: Fragment() {
    var viewLayout: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.fg_php_adduser, container, false)
        return viewLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addUserBtn.setOnClickListener {
            adduser(Gson().toJson(CarUserBean(userName.text.toString(),userPhone.text.toString(),0)))
        }
    }


    //获取服务器时间对比本地时间是否超过或等于15天

    //获取服务器时间对比本地时间是否超过或等于15天
    fun adduser(value:String) {
        ViseLog.e("测试json====="+value)
        //设置登录的url
        RetrofitUrlManager.getInstance().removeGlobalDomain()//移除全据url
        RetrofitUrlManager.getInstance().setGlobalDomain(Api.phpTest_URL)
        val httpUrl2 = RetrofitUrlManager.getInstance().getGlobalDomain()
        ViseLog.e("聚合数据==============" + httpUrl2.toString())

        if (httpUrl2 == null || httpUrl2.toString() != Api.phpTest_URL) { //可以在 App 运行时随意切换某个接口的 BaseUrl
            ViseLog.e("聚合数据==============" + httpUrl2.toString())
            RetrofitUrlManager.getInstance().putDomain(Api.phpTest, Api.phpTest_URL)
        }
//        如果您已经确定了最终的 BaseUrl ,不需要再动态切换 BaseUrl, 请
//        RetrofitUrlManager.getInstance().setRun(false)
        NetWorkManager.instance.phpTestServices!!.adduser(value)
            //设置事件触发在非主线程
            .subscribeOn(Schedulers.io())
            //设置事件接受在UI线程以达到UI显示的目的
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseBody> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseBody) {

                }

                override fun onError(e: Throwable) {
                    ViseLog.e("onError==============" + e.message)
                }
            })
    }

}