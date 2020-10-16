package com.kijlee.wb.fivemdogfood.ui.fivem.main.ui.mine


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.bmob.v3.BmobUser
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.entity.fivem.ManagerUser
import com.kijlee.wb.fivemdogfood.flag.Flag
import com.kijlee.wb.fivemdogfood.flag.FragmentName
import com.kijlee.wb.fivemdogfood.ui.fivem.main.ui.adddate.FiveMAddDateActivity
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
        addDateSm.setOnClickListener {

            if (BmobUser.isLogin()) {
                val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)
                Snackbar.make(addDateSm!!, "已经登录：" + user.getUsername(), Snackbar.LENGTH_LONG).show()
                //跳转到下一级
                var intent = Intent(context, FiveMAddDateActivity::class.java)
                intent.putExtra(Flag.FragmentSwitch, FragmentName.FgAddDateSm)
                startActivity(intent)
            } else {
                Snackbar.make(addDateSm!!, "尚未登录", Snackbar.LENGTH_LONG).show()

                //跳转到下一级
                var intent = Intent(context, FiveMAddDateActivity::class.java)
                intent.putExtra(Flag.FragmentSwitch, FragmentName.FgRegister)
                startActivity(intent)
            }
        }
    }

}