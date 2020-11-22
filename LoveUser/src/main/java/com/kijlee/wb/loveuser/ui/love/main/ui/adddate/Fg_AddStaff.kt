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
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.base.BaseFragment
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.kijlee.wb.loveuser.entity.loveuser.OrgBean
import com.vise.log.ViseLog
import kotlinx.android.synthetic.main.fg_add_org.*

/**
 * 添加员工
 */
class Fg_AddStaff : BaseFragment() {
    var viewLayout: View? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.fg_add_staff, container, false)

        return viewLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //申请机构
        addOrgBtn.setOnClickListener {
            showProgress()
            var orgBean = OrgBean()
            orgBean.orgName = orgName.text.toString()//
//            orgBean.inviteCode = inviteCode.text.toString()//邀请码
            orgBean.checkCode = checkCode.text.toString()//验证码
            if (!TextUtils.isEmpty(orgName.text)) {
                if (!TextUtils.isEmpty(userPhone.text)) {
                    if (!TextUtils.isEmpty(inviteCode.text)) {//有邀请码就去查询父级机构
                        selectParentsOrg(inviteCode.text.toString(), orgBean)
                    } else {
                        upDate(orgBean)
                    }
                }else{
                    showToast("手机号为空不能登录")
                }
            } else {
                showToast("机构名称为空")
            }

        }
    }

    fun selectParentsOrg(inviteCode: String, orgBean: OrgBean) {

        //最后组装完整的and条件
        var selectOrg = BmobQuery<OrgBean>()
        selectOrg.addWhereEqualTo(
                "inviteCode",
                inviteCode
        )
        selectOrg.findObjects(object : FindListener<OrgBean>() {
            override fun done(p0: MutableList<OrgBean>?, p1: BmobException?) {
                hideProgress()
                ViseLog.e("查询结果====="+p0!!.size)
                if (p1 == null) {
                    if (p0 != null && p0.size == 1) {//查询到邀请码的机构得到机构id
                        orgBean.parentsObjectId = p0[0].objectId//父级机构id
                        upDate(orgBean)
                    } else {
                        showToast("邀请码错误")
                    }
                } else {
                    ViseLog.e("查询失败")
                }
            }
        })
    }

    fun upDate(orgBean: OrgBean) {

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
//                                    }

                                orgBean!!.delete(p0, object : UpdateListener() {
                                    override fun done(p0: BmobException?) {

                                        if (p0 == null) {

                                            Snackbar.make(
                                                    viewLayout!!,
                                                    "注册失败：" + e.errorCode + e.message,
                                                    Snackbar.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                })
                            }
                        }
                    })
                }

            }
        })

    }
}