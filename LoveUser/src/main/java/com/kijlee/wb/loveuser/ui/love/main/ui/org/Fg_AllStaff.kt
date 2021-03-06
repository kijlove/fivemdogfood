package com.kijlee.wb.loveuser.ui.love.main.ui.org

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.base.BaseFragment
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.vise.log.ViseLog
import kotlinx.android.synthetic.main.fg_toolbar_recyclerview.*

/**
 * 全部员工列表
 */
class Fg_AllStaff:BaseFragment() {

    var viewLayout: View? = null
    var adapter: StaffsAdapter? = null
    var stringArray: MutableList<ManagerUser>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.fg_toolbar_recyclerview, container, false)
        viewLayout!!.findViewById<QMUITopBarLayout>(R.id.topbar).addLeftBackImageButton().onClick { requireActivity().finish() }
        viewLayout!!.findViewById<QMUITopBarLayout>(R.id.topbar).setTitle("全部员工")
        adapter = StaffsAdapter(R.layout.layout_defaut_text, stringArray!!)
        return viewLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)
        recyclerView.adapter = adapter
        var orgBeanQuery = BmobQuery<ManagerUser>()
        orgBeanQuery.setPage(1, 10)
        orgBeanQuery.addWhereEqualTo("orgId",user.orgId!!.toString())
        orgBeanQuery.findObjects(object : FindListener<ManagerUser>() {
            override fun done(p0: MutableList<ManagerUser>?, p1: BmobException?) {
                ViseLog.e("查询结果=====")
                if (p1 == null) {
                    adapter!!.setList(p0)
                } else {
                    ViseLog.e("查询失败")
                }
            }
        })

        adapter!!.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {

            }
        }

    }

}