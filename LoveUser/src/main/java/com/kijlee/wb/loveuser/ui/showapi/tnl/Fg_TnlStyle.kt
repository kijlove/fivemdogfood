package com.kijlee.wb.loveuser.ui.showapi.tnl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.adapter.VpAdapter
import com.kijlee.wb.loveuser.entity.showapi.ShowapiResBody
import com.kijlee.wb.loveuser.entity.showapi.TnlStyleBean
import kotlinx.android.synthetic.main.layout_tab_viewpager.*
import java.util.ArrayList

/**
 *
 */
class Fg_TnlStyle : Fragment() {
    var viewLayout: View? = null
    lateinit var title: Array<String>
    lateinit var vpAdapter: VpAdapter
    var tnlStyleBean: TnlStyleBean<ShowapiResBody>? = null
    var list_fragment: MutableList<Fragment> = ArrayList()
    var toInfo:ToInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tnlStyleBean = arguments!!.getSerializable("TnlStyleBean") as TnlStyleBean<ShowapiResBody>
        title = tnlStyleBean!!.showapi_res_body.allTypeList.toTypedArray()
        for (item in title) {
            var fgTnlList = Fg_TnlList()
            var bundle = Bundle()
            bundle.putString("type",item)
            fgTnlList.arguments = bundle
            fgTnlList.toInfo = toInfo
            list_fragment.add(fgTnlList)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vpAdapter = VpAdapter(childFragmentManager, list_fragment, title)
        viewLayout = inflater!!.inflate(R.layout.layout_tab_viewpager, container, false)
//        return super.onCreateView(inflater, container, savedInstanceState)
        //设置TabLayout的模式
        viewLayout!!.findViewById<TabLayout>(R.id.tabLayout).setTabMode(TabLayout.MODE_AUTO)
        //为TabLayout添加tab名称   //viewpager加载adapter
        viewLayout!!.findViewById<ViewPager>(R.id.viewPager).setAdapter(vpAdapter)
        //TabLayout加载viewpager
        viewLayout!!.findViewById<TabLayout>(R.id.tabLayout).setupWithViewPager(viewLayout!!.findViewById<ViewPager>(R.id.viewPager))
        return viewLayout
    }
}