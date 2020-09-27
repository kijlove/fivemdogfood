package com.kijlee.wb.yszx.ui.juhe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kijlee.wb.yszx.R
import com.kijlee.wb.yszx.entity.juhe.CarBrand
import com.kijlee.wb.yszx.entity.juhe.CarModels
import com.kijlee.wb.yszx.entity.juhe.CarSeries
import com.kijlee.wb.yszx.entity.juhe.JuheResult
import com.kijlee.wb.yszx.flag.Flag
import com.kijlee.wb.yszx.internet.Api
import com.kijlee.wb.yszx.internet.manager.NetWorkManager
import com.kijlee.wb.yszx.ui.juhe.interfaces.CarInterface
import com.kijlee.wb.yszx.ui.showapi.tnl.ToInfo
import com.vise.log.ViseLog
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

class Fg_CarModelsList : Fragment() {
    var viewLayout: View? = null
    var carInterface: CarInterface? = null
    var adapter: CarModelsAdapter? = null
    val imgList: MutableList<CarModels>? = ArrayList()
    var carSeries:CarSeries? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        carSeries = arguments!!.getSerializable(Flag.GetLastBean) as CarSeries
        viewLayout = inflater!!.inflate(R.layout.layout_recyclerview, container, false)
        adapter = CarModelsAdapter(android.R.layout.simple_spinner_item, imgList!!)
        viewLayout!!.findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
        models()
        adapter!!.setOnItemClickListener { adapter, view, position ->
            ViseLog.e("点击了图片")
        }

        return viewLayout
    }

    //获取服务器时间对比本地时间是否超过或等于15天

    //获取服务器时间对比本地时间是否超过或等于15天
    fun models() {
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
        NetWorkManager.instance.juheService!!.models(
            "a05788cc2dd6ac676d690062bf9489b3",carSeries!!.brandid.toInt()
        )
            //设置事件触发在非主线程
            .subscribeOn(Schedulers.io())
            //设置事件接受在UI线程以达到UI显示的目的
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<JuheResult<MutableList<CarModels>>> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: JuheResult<MutableList<CarModels>>) {
                    adapter!!.setList(response.result)
                }

                override fun onError(e: Throwable) {
                    ViseLog.e("onError==============" + e.message)
                }
            })
    }

}