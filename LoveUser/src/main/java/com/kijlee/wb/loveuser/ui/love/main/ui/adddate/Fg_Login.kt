package com.kijlee.wb.loveuser.ui.love.main.ui.adddate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.base.BaseFragment
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.vise.log.ViseLog
import kotlinx.android.synthetic.main.fg_add_staff.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 添加员工
 */
class Fg_Login : BaseFragment() {
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
        loginBtn.setOnClickListener {

            BmobUser.loginByAccount(userMobleText.text.toString(),userPassword.text.toString(),object :
                LogInListener<ManagerUser>(){
                override fun done(p0: ManagerUser?, p1: BmobException?) {
                    if (p1 == null) {
                        ViseLog.e("登录成功")
                        val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)
                        Snackbar.make(viewLayout!!, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG)
                            .show()
                        activity!!.finish()
                    } else {
                        ViseLog.e("登录失败"+p1.errorCode+p1.message)
                        Snackbar.make(viewLayout!!, "登录失败：" +p1.message, Snackbar.LENGTH_LONG).show()
                    }

                }
            })
        }
    }

}