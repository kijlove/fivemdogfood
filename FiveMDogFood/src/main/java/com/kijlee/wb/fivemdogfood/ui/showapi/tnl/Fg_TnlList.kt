package com.kijlee.wb.fivemdogfood.ui.showapi.tnl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.entity.showapi.TnlModleBean
import com.kijlee.wb.fivemdogfood.entity.showapi.TnlStyleBean
import com.kijlee.wb.fivemdogfood.entity.showapi.TnlStyleModleList
import com.kijlee.wb.fivemdogfood.internet.Api
import com.kijlee.wb.fivemdogfood.internet.manager.NetWorkManager
import com.vise.log.ViseLog
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

//http://route.showapi.com/126-2?type=%E6%AC%A7%E7%BE%8E&order=&page=&showapi_appid=3146&showapi_sign=b2f3a718a48d4e5abb15d159843eb086
class Fg_TnlList : Fragment() {
    var viewLayout: View? = null
    var type: String? = ""
    var adapter: TnlImageAdapter? = null
    var toInfo: ToInfo? = null
    val imgList: MutableList<TnlModleBean>? = ArrayList()
    var page = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        page = 1
        viewLayout = inflater!!.inflate(R.layout.layout_recyclerview, container, false)
        type = arguments!!.getString("type")
        adapter = TnlImageAdapter(R.layout.layout_image_name, imgList!!)
        ViseLog.e("type==============" + type)
        viewLayout!!.findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
        viewLayout!!.findViewById<RecyclerView>(R.id.recyclerView).layoutManager =
            GridLayoutManager(
                context!!,
                3
            )
        contentlist()
        adapter!!.setOnItemClickListener { adapter, view, position ->
            ViseLog.e("点击了图片")
            var item = adapter.getItem(position) as TnlModleBean
            if (item.imgList != null&&item.imgList!!.size>0 ) {
                var bundle = Bundle()
                bundle.putSerializable("TnlModleBean", item)
                toInfo!!.toInfo(bundle)
            } else {
                Toast.makeText(context!!, "没有子集图片", Toast.LENGTH_SHORT).show()
            }
        }

        adapter!!.loadMoreModule.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                page++
                contentlist()
            }
        })
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        adapter!!.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        return viewLayout
    }

    //获取服务器时间对比本地时间是否超过或等于15天
    fun contentlist() {
        //设置登录的url
        RetrofitUrlManager.getInstance().removeGlobalDomain()//移除全据url
        RetrofitUrlManager.getInstance().setGlobalDomain(Api.showapi_URL)
        val httpUrl2 = RetrofitUrlManager.getInstance().getGlobalDomain()

        if (httpUrl2 == null || httpUrl2.toString() != Api.showapi_URL) { //可以在 App 运行时随意切换某个接口的 BaseUrl
            RetrofitUrlManager.getInstance().putDomain(Api.showapi, Api.showapi_URL)
        }
//        如果您已经确定了最终的 BaseUrl ,不需要再动态切换 BaseUrl, 请
//        RetrofitUrlManager.getInstance().setRun(false)
        NetWorkManager.instance.showApiServices!!.contentlist(
            "b2f3a718a48d4e5abb15d159843eb086",
            "3146", type, "1", page.toString()
        )
            //设置事件触发在非主线程
            .subscribeOn(Schedulers.io())
            //设置事件接受在UI线程以达到UI显示的目的
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<TnlStyleBean<TnlStyleModleList>> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: TnlStyleBean<TnlStyleModleList>) {
                    if (response.showapi_res_body.pagebean.currentPage == 1) {//第一页
                        adapter!!.setList(response.showapi_res_body.pagebean.contentlist.toMutableList())
                    } else {
                        adapter!!.addData(response.showapi_res_body.pagebean.contentlist.toMutableList())
                    }
                    //最后一页
                    if (response.showapi_res_body.pagebean.currentPage ==
                        response.showapi_res_body.pagebean.allPages) {//全部页数//当前页
                        ViseLog.e("最后一页=============="+type)
                        adapter!!.loadMoreModule.loadMoreEnd(false)
                    } else if (response.showapi_res_body.pagebean.currentPage >
                        response.showapi_res_body.pagebean.allPages) {//全部页数//当前页
                        ViseLog.e("最后一页=============="+type)
                        adapter!!.loadMoreModule.loadMoreEnd(false)
                    } else {
                        ViseLog.e("loadMoreComplete=============="+type)
                        adapter!!.loadMoreModule.loadMoreComplete()
                    }
                }

                override fun onError(e: Throwable) {
                    ViseLog.e("onError==============" + e.message)
                }
            })
    }

}