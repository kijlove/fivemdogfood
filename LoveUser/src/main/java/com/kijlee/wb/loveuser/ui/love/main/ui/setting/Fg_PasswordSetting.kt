package com.kijlee.wb.loveuser.ui.love.main.ui.setting

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
import kotlinx.android.synthetic.main.fg_password_setting.*

/***
 * 密码修改
 */
class Fg_PasswordSetting : BaseFragment() {
    var viewLayout: View? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.fg_password_setting, container, false)

        return viewLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)

        //申请机构
        changePasswordBtn.setOnClickListener {
            showProgress()
            user.setPassword(passwordAgainText.text.toString())
            upDate(user)
        }
    }


    fun upDate(orgBean: ManagerUser) {

        orgBean.update(object : UpdateListener() {
            override fun done(p0: BmobException?) {
                if (p0 == null) {
                    Snackbar.make(
                            viewLayout!!,
                            "修改成功",
                            Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

    }
}