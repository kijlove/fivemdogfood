package com.kijlee.wb.fivemdogfood.ui.showapi.tnl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.adapter.ImageAdapter
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

class Fg_TnlInfo : Fragment() {
    var viewLayout: View? = null
    var adapter: ImageAdapter? = null
//    val imgList: MutableList<TnlModleBean>? = ArrayList()
    var tnlModleBean: TnlModleBean? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.layout_recyclerview, container, false)
        tnlModleBean =arguments!!.getSerializable("TnlModleBean")  as TnlModleBean
        adapter = ImageAdapter(R.layout.layout_image_name, tnlModleBean!!.imgList!!.toMutableList())
        viewLayout!!.findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
        viewLayout!!.findViewById<RecyclerView>(R.id.recyclerView).layoutManager =
            StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        return viewLayout
    }

}