package com.kijlee.wb.loveuser.ui.love.main.ui.adddate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.base.BaseFragment
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.kijlee.wb.loveuser.entity.loveuser.OrgBean
import com.vise.log.ViseLog
import kotlinx.android.synthetic.main.fg_add_org.*
import kotlinx.android.synthetic.main.fg_add_org.userPassword
import kotlinx.android.synthetic.main.fg_add_org.userPhone
import kotlinx.android.synthetic.main.fg_register.*

/**
 * 新增机构界面
 */
class Fg_AddOrg : BaseFragment() {
    var viewLayout: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.fg_add_org, container, false)

        return viewLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //申请机构
        addOrgBtn.setOnClickListener {
            showProgress()
            var orgBean = OrgBean(orgName.text.toString(), inviteCode.text.toString(), null, false)
            orgBean.save(object : SaveListener<String>() {
                override fun done(p0: String?, p1: BmobException?) {
                    hideProgress()
                    if (p1 == null) {

                        val user = ManagerUser()
                        user.setUsername(userPhone.text.toString())
                        user.setPassword(userPassword.text.toString())
                        user.orgId = p0
                        user.role = 1
                        user.signUp(object : SaveListener<BmobUser>() {
                            override fun done(user: BmobUser?, e: BmobException?) {
                                if (e == null) {
                                    Snackbar.make(
                                        addOrgBtn,
                                        "申请已提交待审核" + p0,
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                    activity!!.finish()
                                } else {
                                    ViseLog.e("注册失败：" + e.errorCode + e.message)
//                                    if (e.errorCode == 202) {
//                                        Snackbar.make(viewLayout!!, "注册成功", Snackbar.LENGTH_LONG)
//                                            .show()
//                                        activity!!.finish()
//                                    } else {
                                        Snackbar.make(
                                            viewLayout!!,
                                            "注册失败：" + e.errorCode + e.message,
                                            Snackbar.LENGTH_LONG
                                        ).show()
//                                    }
                                }
                            }
                        })
                    }

                }
            })

        }
    }

}