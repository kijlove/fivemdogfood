package com.kijlee.wb.loveuser.ui.love.main.ui.mine


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.base.BaseFragment
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.kijlee.wb.loveuser.entity.loveuser.OrgBean
import com.kijlee.wb.loveuser.entity.loveuser.RoleCode
import com.kijlee.wb.loveuser.flag.Flag
import com.kijlee.wb.loveuser.flag.FragmentName
import com.kijlee.wb.loveuser.ui.love.main.ui.adddate.MineSwitchActivity
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.vise.log.ViseLog
import kotlinx.android.synthetic.main.fg_add_org.*
import kotlinx.android.synthetic.main.fg_mine.*


/**
 *
 */
class FgMine : BaseFragment() {
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
        findMyOrg()
        //获取全部人员列表
        getAllStaffText.setOnClickListener {

            //跳转到下一级
            var intent = Intent(context, MineSwitchActivity::class.java)
            intent.putExtra(Flag.FragmentSwitch, FragmentName.Fg_AllStaff)
            startActivity(intent)
        }
        addDateLoveUser.setOnClickListener {
            if (BmobUser.isLogin()) {
                val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)
                Snackbar.make(addDateLoveUser!!, "已经登录：" + user.getUsername(), Snackbar.LENGTH_LONG)
                        .show()
                //跳转到下一级
                var intent = Intent(context, MineSwitchActivity::class.java)
                intent.putExtra(Flag.FragmentSwitch, FragmentName.FgAddDateSm)
                startActivity(intent)
            } else {
                Snackbar.make(addDateLoveUser!!, "尚未登录", Snackbar.LENGTH_LONG).show()
            }
        }
        //设置邀请码
        setInviteCode.setOnClickListener {
            if (BmobUser.isLogin()) {

                val builder = QMUIDialog.EditTextDialogBuilder(requireContext())
                builder.setTitle("设置邀请码可以添加加盟机构")
                        .addAction(
                                0,
                                "取消",
                                QMUIDialogAction.ACTION_PROP_POSITIVE
                        ) { dialog, index -> dialog.dismiss() }
                        .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_NEGATIVE) { dialog, index ->
                            val text = builder.editText.text
                            if (text != null && text.length > 0) {
                                dialog.dismiss()
                                setInviteCode(text.toString())
                            } else {
                                Toast.makeText(activity, "输入管理员账号", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .create().show()
            } else {
                Snackbar.make(addDateLoveUser!!, "尚未登录", Snackbar.LENGTH_LONG).show()
            }
        }
        //设置验证码
        setManagerIdText.setOnClickListener {
            if (BmobUser.isLogin()) {

                val builder = QMUIDialog.EditTextDialogBuilder(requireContext())
                builder.setTitle("设置验证码用于查看客户列表")
                        .addAction(
                                0,
                                "取消",
                                QMUIDialogAction.ACTION_PROP_POSITIVE
                        ) { dialog, index -> dialog.dismiss() }
                        .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_NEGATIVE) { dialog, index ->
                            val text = builder.editText.text
                            if (text != null && text.length > 0) {
                                dialog.dismiss()
                                setManagerIdText(text.toString())
                            } else {
                                Toast.makeText(activity, "输入管理员账号", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .create().show()
            } else {
                Snackbar.make(addDateLoveUser!!, "尚未登录", Snackbar.LENGTH_LONG).show()
            }
        }

        logonOutText.setOnClickListener {
            BmobUser.logOut()
            if (!BmobUser.isLogin()) {
                Snackbar.make(addDateLoveUser!!, "退出成功", Snackbar.LENGTH_LONG).show()
                registerText.visibility = View.VISIBLE
                logonInText.visibility = View.VISIBLE
                logonOutLinear.visibility = View.GONE
            }
        }
        registerText.setOnClickListener {
            //跳转到下一级
            var intent = Intent(context, MineSwitchActivity::class.java)
            intent.putExtra(Flag.FragmentSwitch, FragmentName.FgRegister)
            startActivity(intent)
        }

        //跳转全部
        getAllOrgText.setOnClickListener {
            //跳转到下一级
            var intent = Intent(context, MineSwitchActivity::class.java)
            intent.putExtra(Flag.FragmentSwitch, FragmentName.Fg_AllOrg)
            startActivity(intent)
        }
        logonInText.setOnClickListener {
            //跳转到下一级
            var intent = Intent(context, MineSwitchActivity::class.java)
            intent.putExtra(Flag.FragmentSwitch, FragmentName.FgLogin)
            startActivity(intent)
        }
        changePasswordText.setOnClickListener {
            //跳转到下一级
            var intent = Intent(context, MineSwitchActivity::class.java)
            intent.putExtra(Flag.FragmentSwitch, FragmentName.Fg_PasswordSetting)
            startActivity(intent)
        }

    }

    fun setInviteCode(inviteCode: String) {

        //最后组装完整的and条件
        var selectOrg = BmobQuery<OrgBean>()
        selectOrg.addWhereEqualTo(
                "objectId",
                BmobUser.getCurrentUser(ManagerUser::class.java).orgId//机构id
        )
        selectOrg.findObjects(object : FindListener<OrgBean>() {
            override fun done(p0: MutableList<OrgBean>?, p1: BmobException?) {
                hideProgress()
                ViseLog.e("查询结果=====" + p0!!.size)
                if (p1 == null) {
                    if (p0 != null && p0.size == 1) {//查询到邀请码的机构得到机构id
                        p0[0].inviteCode = inviteCode//父级机构id
                        p0[0].update(object : UpdateListener() {
                            override fun done(p0: BmobException?) {
                                if (p0 == null) {
                                    Snackbar.make(
                                            addOrgBtn,
                                            "更新完成",
                                            Snackbar.LENGTH_LONG
                                    ).show()
                                    activity!!.finish()
                                }
                            }
                        })

                    } else {
                        showToast("邀请码错误")
                    }
                } else {
                    ViseLog.e("查询失败")
                }
            }
        })
    }
    fun setManagerIdText(managerId: String) {

        //最后组装完整的and条件
        var selectOrg = BmobQuery<OrgBean>()
        selectOrg.addWhereEqualTo(
                "objectId",
                BmobUser.getCurrentUser(ManagerUser::class.java).orgId//机构id
        )
        selectOrg.findObjects(object : FindListener<OrgBean>() {
            override fun done(p0: MutableList<OrgBean>?, p1: BmobException?) {
                hideProgress()
                ViseLog.e("查询结果=====" + p0!!.size)
                if (p1 == null) {
                    if (p0 != null && p0.size == 1) {//查询到邀请码的机构得到机构id
                        p0[0].checkCode = managerId//父级机构id
                        p0[0].update(object : UpdateListener() {
                            override fun done(p0: BmobException?) {
                                if (p0 == null) {
                                    Snackbar.make(
                                            addOrgBtn,
                                            "更新完成",
                                            Snackbar.LENGTH_LONG
                                    ).show()
                                    activity!!.finish()
                                }
                            }
                        })

                    } else {
                        showToast("邀请码错误")
                    }
                } else {
                    ViseLog.e("查询失败")
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        if (BmobUser.isLogin()) {
            logonOutLinear.visibility = View.VISIBLE
            registerText.visibility = View.GONE
            logonInText.visibility = View.GONE
            logonOutText.visibility = View.VISIBLE
            userNameText.visibility = View.VISIBLE
            changePasswordText.visibility = View.VISIBLE

            when(BmobUser.getCurrentUser(ManagerUser::class.java).roleCode){
                RoleCode.BOSS->{//老板
                    getAllStaffText.visibility = View.VISIBLE
                    setInviteCode.visibility = View.VISIBLE
                    setManagerIdText.visibility = View.VISIBLE
                    getAllOrgText.visibility = View.VISIBLE
                    addDateLoveUser.visibility = View.GONE
                }
                RoleCode.EDITER->{//录入者
                    setInviteCode.visibility = View.GONE
                    getAllStaffText.visibility = View.GONE
                    getAllOrgText.visibility = View.GONE
                    setManagerIdText.visibility = View.GONE
                    addDateLoveUser.visibility = View.VISIBLE

                }
            }
        } else {
            logonOutLinear.visibility = View.GONE
            registerText.visibility = View.VISIBLE
            logonInText.visibility = View.VISIBLE
        }
    }

    fun findMyOrg() {
        if(BmobUser.isLogin()){
            val user: ManagerUser? = BmobUser.getCurrentUser(ManagerUser::class.java)
            ViseLog.e("user.username=="+user!!.username)
            userNameText.text = user!!.username

            var query = BmobQuery<OrgBean>()
            query.addWhereEqualTo("objectId",user.orgId!!.toString())
            query.findObjects(object : FindListener<OrgBean>() {
                override fun done(p0: MutableList<OrgBean>?, p1: BmobException?) {
                    ViseLog.e("查询结果====="+p0!![0].objectId)
                    userNameText.text = user.username.toString()
                    if (p1 == null) {
                        userNameText.text = user.username.toString()+
                                if(p0!![0].isOpen!!){
                                    "机构已启用"
                                }else{
                                    "机构未启用"
                                }
                    } else {
                        ViseLog.e("查询失败"+p1.errorCode)
                        ViseLog.e("查询失败"+user.orgId!!.toString())
                    }
                }
            })
        }

    }
}