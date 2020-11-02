package com.kijlee.wb.loveuser.ui.love.main.ui.mine


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.bmob.v3.BmobUser
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.kijlee.wb.loveuser.flag.Flag
import com.kijlee.wb.loveuser.flag.FragmentName
import com.kijlee.wb.loveuser.ui.love.main.ui.adddate.FiveMAddDateActivity
import kotlinx.android.synthetic.main.fg_mine.*


/**
 *
 */
class FgMine : Fragment() {
    var viewLayout: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.fg_mine, container, false)
        return viewLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addDateLoveUser.setOnClickListener {
            if (BmobUser.isLogin()) {
                val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)
                Snackbar.make(addDateLoveUser!!, "已经登录：" + user.getUsername(), Snackbar.LENGTH_LONG)
                    .show()
                //跳转到下一级
                var intent = Intent(context, FiveMAddDateActivity::class.java)
                intent.putExtra(Flag.FragmentSwitch, FragmentName.FgAddDateSm)
                startActivity(intent)
            } else {
                Snackbar.make(addDateLoveUser!!, "尚未登录", Snackbar.LENGTH_LONG).show()
            }
        }

        logonOutText.setOnClickListener {
            BmobUser.logOut()

            if (!BmobUser.isLogin()) {
                Snackbar.make(addDateLoveUser!!, "退出成功", Snackbar.LENGTH_LONG).show()
                registerText.visibility=View.VISIBLE
                logonInText.visibility=View.VISIBLE
                logonOutText.visibility=View.GONE
                userNameText.visibility=View.GONE
                userNameText.text = ""
            }
        }
        registerText.setOnClickListener {
            //跳转到下一级
            var intent = Intent(context, FiveMAddDateActivity::class.java)
            intent.putExtra(Flag.FragmentSwitch, FragmentName.FgRegister)
            startActivity(intent)
        }
        logonInText.setOnClickListener {
            //跳转到下一级
            var intent = Intent(context, FiveMAddDateActivity::class.java)
            intent.putExtra(Flag.FragmentSwitch, FragmentName.FgLogonIn)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        if (BmobUser.isLogin()) {
            val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)
            registerText.visibility=View.GONE
            logonInText.visibility=View.GONE
            logonOutText.visibility=View.VISIBLE
            userNameText.visibility=View.VISIBLE
            userNameText.text = user.username.toString()
        } else {
            registerText.visibility=View.VISIBLE
            logonInText.visibility=View.VISIBLE
            logonOutText.visibility=View.GONE
            userNameText.visibility=View.GONE
        }
    }
}