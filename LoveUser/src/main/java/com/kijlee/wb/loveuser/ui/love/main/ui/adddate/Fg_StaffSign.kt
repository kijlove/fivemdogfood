package com.kijlee.wb.loveuser.ui.love.main.ui.adddate

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.base.BaseFragment
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.kijlee.wb.loveuser.entity.loveuser.OrgBean
import com.kijlee.wb.loveuser.entity.loveuser.RoleCode
import com.vise.log.ViseLog

import kotlinx.android.synthetic.main.fg_register.*
/**
 * 员工注册
 */
class Fg_StaffSign : BaseFragment() {
    var viewLayout: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.fg_register, container, false)
        return viewLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addUserBtn.setOnClickListener {
            if (!TextUtils.isEmpty(inviteCode.text)) {//有邀请码就去查询父级机构
                selectParentsOrg(inviteCode.text.toString())
            }
        }

    }

    fun selectParentsOrg(inviteCode: String) {

        //最后组装完整的and条件
        var selectOrg = BmobQuery<OrgBean>()
        selectOrg.addWhereEqualTo(
            "inviteCode",
            inviteCode
        )
        selectOrg.findObjects(object : FindListener<OrgBean>() {
            override fun done(p0: MutableList<OrgBean>?, p1: BmobException?) {
                hideProgress()
                ViseLog.e("查询结果=====" + p0!!.size)
                if (p1 == null) {
                    if (p0 != null && p0.size == 1) {//查询到邀请码的机构得到机构id
                        if (p0[0].isOpen!!) {

                            var staffBean = ManagerUser()
                            when (roleSpinner.selectedItem.toString()) {
                                "编辑" -> {
                                    staffBean.roleCode = RoleCode.EDITER
                                    staffBean.role = 99
                                }
                            }
                            staffBean.orgId = p0[0].objectId.toString()
                            staffBean.realName = userNameEdit.text.toString()
                            staffBean.username = userPhone.text.toString()
                            staffBean.setPassword(
                                userPhone.text.toString().substring(
                                    userPhone.text.toString().length - 6,
                                    userPhone.text.toString().length
                                )
                            )
                            ViseLog.e(
                                userPhone.text.toString().substring(
                                    userPhone.text.toString().length - 6,
                                    userPhone.text.toString().length
                                )
                            )
                            staffBean.mobilePhoneNumber = userPhone.text.toString()
                            staffBean.signUp(object : SaveListener<BmobUser>() {
                                override fun done(user: BmobUser?, e: BmobException?) {
                                    if (e == null) {
                                        Snackbar.make(viewLayout!!, "注册成功", Snackbar.LENGTH_LONG)
                                            .show()
                                        activity!!.finish()
                                    } else {
                                        ViseLog.e("注册失败：" + e.errorCode + e.message)
                                        if (e.errorCode == 202) {
                                            Snackbar.make(
                                                viewLayout!!,
                                                "注册成功",
                                                Snackbar.LENGTH_LONG
                                            ).show()
                                            activity!!.finish()
                                        } else {
                                            Snackbar.make(
                                                viewLayout!!,
                                                "注册失败：" + e.errorCode + e.message,
                                                Snackbar.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                            })
                        } else {
                            showToast("加盟机构尚未营业")
                        }
                    } else {
                        showToast("邀请码错误")
                    }
                } else {
                    ViseLog.e("查询失败")
                }
            }
        })
    }

}