package com.kijlee.wb.fivemdogfood.ui.juhe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.entity.juhe.CarBrand
import com.kijlee.wb.fivemdogfood.entity.juhe.CarSeries
import com.kijlee.wb.fivemdogfood.entity.juhe.JuheResult
import com.kijlee.wb.fivemdogfood.flag.Flag
import com.kijlee.wb.fivemdogfood.internet.Api
import com.kijlee.wb.fivemdogfood.internet.manager.NetWorkManager
import com.kijlee.wb.fivemdogfood.ui.juhe.interfaces.CarInterface
import com.vise.log.ViseLog
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

class Fg_CarSeriesList  : Fragment(), AdapterView.OnItemSelectedListener  {
    var viewLayout: View? = null

    var adapter: CarSeriesAdapter? = null
    val imgList: MutableList<CarSeries>? = ArrayList()
    var carInterface:CarInterface? =null
    var carBrand:CarBrand? = null
    var array:Array<String> = arrayOf("微型车","小型车","紧凑型车","中型车",
        "中大型车","大型车","跑车","MPV","微面","微卡","轻客","低端皮卡","高端皮卡",
        "小型SUV","紧凑型SUV","中型SUV","中大型SUV","大型SUV","紧凑型MPV")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        carBrand = arguments!!.getSerializable(Flag.GetLastBean) as CarBrand
        viewLayout = inflater!!.inflate(R.layout.fragment_home, container, false)
        adapter = CarSeriesAdapter(android.R.layout.simple_spinner_item, imgList!!)
        viewLayout!!.findViewById<Spinner>(R.id.local).adapter = ArrayAdapter<String>(context!!,android.R.layout.simple_spinner_item,array)
        viewLayout!!.findViewById<RecyclerView>(R.id.recyclerViewUser).adapter = adapter
        series()
        adapter!!.setOnItemClickListener { adapter, view, position ->
            ViseLog.e("点击了图片")

            var bundle = Bundle()
            bundle.putSerializable(Flag.ToNextBean,adapter.getItem(position) as CarSeries)
            carInterface!!.toCarModels(bundle)
        }
        viewLayout!!.findViewById<Spinner>(R.id.local).onItemSelectedListener = this
        return viewLayout
    }

    //获取服务器时间对比本地时间是否超过或等于15天

    //获取服务器时间对比本地时间是否超过或等于15天
    fun series() {
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
        NetWorkManager.instance.juheService!!.series(
            "a05788cc2dd6ac676d690062bf9489b3",
            carBrand!!.id.toInt(),
            viewLayout!!.findViewById<Spinner>(R.id.local).selectedItemPosition+1
        )
            //设置事件触发在非主线程
            .subscribeOn(Schedulers.io())
            //设置事件接受在UI线程以达到UI显示的目的
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<JuheResult<MutableList<CarSeries>>> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: JuheResult<MutableList<CarSeries>>) {
                    ViseLog.e("聚合数据==============" + viewLayout!!.findViewById<Spinner>(R.id.local).selectedItemPosition)
                    adapter!!.setList(response.result)
                }

                override fun onError(e: Throwable) {
                    ViseLog.e("onError==============" + e.message)
                }
            })
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        series()    }

}