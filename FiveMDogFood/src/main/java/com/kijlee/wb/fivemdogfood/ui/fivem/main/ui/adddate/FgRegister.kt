package com.kijlee.wb.fivemdogfood.ui.fivem.main.ui.adddate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.entity.fivem.ManagerUser
import kotlinx.android.synthetic.main.fg_register.*


class FgRegister : Fragment() {
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
            val user = ManagerUser()
            user.setUsername(userPhone.text.toString())
            user.setPassword(userPassword.text.toString())

            user.signUp(object : SaveListener<ManagerUser>() {
                override fun done(user: ManagerUser, e: BmobException) {
                    if (e == null) {
                        Snackbar.make(view, "注册成功", Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(view, "尚未失败：" + e.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }
        logonBtn.setOnClickListener {
            val user = ManagerUser()
            user.setUsername(userPhone.text.toString())
            user.setPassword(userPassword.text.toString())
            user.login(object : SaveListener<ManagerUser>() {
                override fun done(bmobUser: ManagerUser, e: BmobException) {
                    if (e == null) {
                        val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)
                        Snackbar.make(view, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG)
                            .show()
                    } else {
                        Snackbar.make(view, "登录失败：" + e.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }
    }}