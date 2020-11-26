package com.kijlee.wb.loveuser.ui.love.main.ui.adddate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.base.BaseFragment
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.kijlee.wb.loveuser.entity.loveuser.StaffBean
import com.vise.log.ViseLog
import kotlinx.android.synthetic.main.fg_add_org.*
import kotlinx.android.synthetic.main.fg_add_staff.*

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
        addStaffBtn.setOnClickListener {

            //申请机构
            val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)

            var staffBean = StaffBean()
            when(roleSpinner.selectedItem.toString()){
                "编辑"->{
                    staffBean.role = "editer"
                }
            }
            staffBean.oid = user.orgId.toString()
            staffBean.userName = userNameEdit.text.toString()
            staffBean.moblePhone = userMobleText.text.toString()
            staffBean.save(object : SaveListener<String>() {
                override fun done(p0: String?, p1: BmobException?) {
                    hideProgress()
                    if (p1 == null) {

                        Snackbar.make(
                            viewLayout!!,
                            "注册成功" ,
                            Snackbar.LENGTH_LONG
                        ).show()

                    }

                }
            })
        }
    }

}