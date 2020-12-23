package com.kijlee.wb.loveuser.ui.love.main.ui.adddate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.adapter.VpAdapter
import com.kijlee.wb.loveuser.base.BaseFragment
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*

/**
 * 人员注册和机构注册
 */
class FgRegister : BaseFragment() {
    var viewLayout: View? = null
    var fgStaffSign: Fg_StaffSign? = Fg_StaffSign()//员工注册
    var fgOrgSign: Fg_OrgSign? = Fg_OrgSign()//机构注册
    var list_fragment: MutableList<Fragment> = ArrayList()
    var title: Array<String> = arrayOf("机构注册","员工注册")
    lateinit var vpAdapter: VpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            list_fragment.add(fgOrgSign!!)
            list_fragment.add(fgStaffSign!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.layout_toolbar_tablayout, container, false)
        vpAdapter = VpAdapter(childFragmentManager, list_fragment, title)
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