package com.kijlee.wb.loveuser.ui.love.main.ui.adddate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.SaveListener
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.kijlee.wb.loveuser.flag.Flag
import com.kijlee.wb.loveuser.flag.FragmentName
import com.vise.log.ViseLog
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
        when(requireArguments().getString(Flag.FragmentSwitch)){
            FragmentName.FgRegister->{
                addUserBtn.visibility = View.VISIBLE
                logonBtn.visibility = View.GONE
                managerLinear.visibility = View.VISIBLE
            }
            FragmentName.FgLogonIn->{
                addUserBtn.visibility = View.GONE
                logonBtn.visibility = View.VISIBLE
                managerLinear.visibility = View.GONE
            }
        }
        addUserBtn.setOnClickListener {
            val user = ManagerUser()
            user.setUsername(userPhone.text.toString())
            user.setPassword(userPassword.text.toString())
            user.managerId=managerId.text.toString()
            user.role = 1
            user.signUp(object : SaveListener<BmobUser>() {
                override fun done(user: BmobUser?, e: BmobException?) {
                    if (e == null) {
                        ViseLog.e("注册成功")
                        activity!!.finish()
                    } else {
                        ViseLog.e("注册失败：" + e.errorCode+e.message)
                        if(e.errorCode == 202){
                            Snackbar.make(viewLayout!!, "注册成功", Snackbar.LENGTH_LONG).show()
                            activity!!.finish()
                        }else{
                            Snackbar.make(viewLayout!!, "注册失败：" + e.errorCode+e.message, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            })

        }
        logonBtn.setOnClickListener {

            BmobUser.loginByAccount(userPhone.text.toString(),userPassword.text.toString(),object :
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
    }}