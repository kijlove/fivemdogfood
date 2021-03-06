package com.kijlee.wb.loveuser.ui.love.main.ui.org

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.base.BaseFragment
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.kijlee.wb.loveuser.entity.loveuser.OrgBean
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.vise.log.ViseLog

import kotlinx.android.synthetic.main.fg_toolbar_recyclerview.*

/***
 * 全部机构
 */
class Fg_AllOrg : BaseFragment() {
    var viewLayout: View? = null
    var adapter: OrgListAdapter? = null
    var stringArray: MutableList<OrgBean>? = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.fg_toolbar_recyclerview, container, false)
        viewLayout!!.findViewById<QMUITopBarLayout>(R.id.topbar).addLeftBackImageButton().onClick { requireActivity().finish() }
        viewLayout!!.findViewById<QMUITopBarLayout>(R.id.topbar).setTitle("全部机构")
        adapter = OrgListAdapter(R.layout.re_item_org_approve, stringArray!!)
        return viewLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)
        recyclerView.adapter = adapter
        var orgBeanQuery = BmobQuery<OrgBean>()
        orgBeanQuery.setPage(1, 10)
        orgBeanQuery.addWhereEqualTo("parentsObjectId",user.orgId!!.toString())
        orgBeanQuery.findObjects(object : FindListener<OrgBean>() {
            override fun done(p0: MutableList<OrgBean>?, p1: BmobException?) {
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
                R.id.okBtn -> {//通过
                    upDateOrg(true, adapter.getItem(position) as OrgBean)
                }

                R.id.cancleBtn -> {//驳回
                    upDateOrg(false, adapter.getItem(position) as OrgBean)
                }
            }
        }

    }

    //通过还是驳回
    fun upDateOrg(isOpen: Boolean, orgBean: OrgBean) {
        var qmuiDialog = QMUIDialog.MessageDialogBuilder(context)
        qmuiDialog.setTitle("提示").setMessage(if (isOpen) {
            "确定添加此机构吗?"
        } else {
            "确定驳回此机构吗?"
        })
                .addAction(
                        0,
                        "取消",
                        QMUIDialogAction.ACTION_PROP_POSITIVE
                ) { dialog, index -> dialog.dismiss() }
                .addAction(
                        0,
                        "确定",
                        QMUIDialogAction.ACTION_PROP_NEGATIVE
                ) { dialog, index ->
                    orgBean.isOpen = isOpen
                    if (orgBean.isOpen!!) {
                        orgBean.update(orgBean.objectId, object : UpdateListener() {
                            override fun done(p0: BmobException?) {
                                if (p0 == null) {
                                    Snackbar.make(
                                        recyclerView,
                                            "该机构已开通",
                                            Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        })

                    } else {
                        orgBean.delete(orgBean.objectId, object : UpdateListener() {
                            override fun done(p0: BmobException?) {
                                if (p0 == null) {
                                    Snackbar.make(
                                        recyclerView,
                                            "该机构删除",
                                            Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        })
                    }
                    dialog.dismiss()
                }
                .create().show()
    }
}