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
import com.qmuiteam.qmui.widget.QMUITopBarLayout
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
        viewLayout!!.findViewById<QMUITopBarLayout>(R.id.topbar).setTitle("我的")
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
                showProgress()
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
                logonOutLinear.visibility = View.GONE
            }
        }

        //跳转全部
        getAllOrgText.setOnClickListener {
            //跳转到下一级
            var intent = Intent(context, MineSwitchActivity::class.java)
            intent.putExtra(Flag.FragmentSwitch, FragmentName.Fg_AllOrg)
            startActivity(intent)
        }
        changePasswordText.setOnClickListener {
            //跳转到下一级
            var intent = Intent(context, MineSwitchActivity::class.java)
            intent.putExtra(Flag.FragmentSwitch, FragmentName.Fg_PasswordSetting)
            startActivity(intent)
        }

    }

    //设置邀请码  首先得查询是否有相同机构的邀请码
    fun setInviteCode(inviteCode: String) {
        //最后组装完整的and条件
        var selectOrg = BmobQuery<OrgBean>()
        selectOrg.addWhereEqualTo(
                "objectId",
                BmobUser.getCurrentUser(ManagerUser::class.java).orgId//机构id
        )//查询出登陆这所在的机构名称  然后设置机构的邀请id
        selectOrg.findObjects(object : FindListener<OrgBean>() {
            override fun done(data: MutableList<OrgBean>?, p1: BmobException?) {
                ViseLog.e("查询结果=====" + data!!.size)
                if (p1 == null) {
                    if (data != null && data.size == 1) {//查询到登陆人的机构得到机构类
                        //机构邀请码等于空 说明没设置
                        if(data[0].inviteCode == null){
                            data[0].inviteCode = inviteCode//邀请码
                        }else if(data[0].inviteCode.equals(inviteCode)){//如果邀请码 等于 设置的邀请码
                            Snackbar.make(
                                    logonOutText,
                                    "与原邀请码相同",
                                    Snackbar.LENGTH_LONG
                            ).show()
                        }else{
                            data[0].inviteCode = inviteCode//邀请码
                        }
                        //去查询邀请码是否和其他机构相同
                        var selectInviteCode = BmobQuery<OrgBean>()
                        selectInviteCode.addWhereEqualTo(
                                "inviteCode",
                                inviteCode//邀请id
                        )
                        selectInviteCode.findObjects(object : FindListener<OrgBean>() {
                            override fun done(p0: MutableList<OrgBean>?, p1: BmobException?) {
                                if (p1 == null) {
                                    ViseLog.e("查询结果=====" + p0!!.size)
                                    if (p0!!.size == 0) {//说明没有邀请码与设置的相同 就保存并上传新数据

                                        //保存邀请码
                                        data[0].update(object : UpdateListener() {
                                            override fun done(result: BmobException?) {
                                                if (result == null) {
                                                    hideProgress()
                                                    Snackbar.make(
                                                            logonOutText,
                                                            "更新完成",
                                                            Snackbar.LENGTH_LONG
                                                    ).show()

                                                }
                                            }
                                        })
                                    } else {//否则就给出提示说 邀请码相同或者错误

                                        hideProgress()
                                        showToast("邀请码错误或者已存在")
                                    }
                                } else {
                                    ViseLog.e("查询结果=====" + p1.toString())
                                }
                            }
                        })

                    } else {

                        hideProgress()
                        showToast("邀请码错误")
                    }
                } else {
                    ViseLog.e("查询失败"+BmobUser.getCurrentUser(ManagerUser::class.java).orgId+"错误信息=="+p1.toString())
                }
            }
        })

    }


    //设置验证码
    fun setManagerIdText(checkCode: String) {

        //最后组装完整的and条件
        var selectOrg = BmobQuery<OrgBean>()
        selectOrg.addWhereEqualTo(
                "objectId",
                BmobUser.getCurrentUser(ManagerUser::class.java).orgId//机构id
        )
        selectOrg.findObjects(object : FindListener<OrgBean>() {
            override fun done(data: MutableList<OrgBean>?, p1: BmobException?) {

                ViseLog.e("查询结果=====" + data!!.size)
                if (p1 == null) {
                    if (data != null && data.size == 1) {//查询到登陆人的机构得到机构类

                        if(data[0].checkCode == null){
                            data[0].checkCode = checkCode//邀请码
                        }else if(data[0].checkCode.equals(checkCode)){//如果邀请码 等于 设置的邀请码
                            Snackbar.make(
                                    logonOutText,
                                    "与原验证码相同",
                                    Snackbar.LENGTH_LONG
                            ).show()
                        }else{
                            data[0].checkCode = checkCode//邀请码
                        }


                        //去查询邀请码是否和其他机构相同
                        var selectInviteCode = BmobQuery<OrgBean>()
                        selectInviteCode.addWhereEqualTo(
                                "checkCode",
                                checkCode//验证码
                        )
                        selectInviteCode.findObjects(object : FindListener<OrgBean>() {
                            override fun done(p0: MutableList<OrgBean>?, p1: BmobException?) {
                                if (p1 == null) {
                                    ViseLog.e("查询结果=====" + p0!!.size)
                                    if (p0!!.size == 0) {//说明没有邀请码与设置的相同 就保存并上传新数据

                                        //保存邀请码
                                        data[0].update(object : UpdateListener() {
                                            override fun done(result: BmobException?) {
                                                if (result == null) {
                                                    hideProgress()
                                                    Snackbar.make(
                                                            logonOutText,
                                                            "更新完成",
                                                            Snackbar.LENGTH_LONG
                                                    ).show()

                                                }
                                            }
                                        })
                                    } else {//否则就给出提示说 邀请码相同或者错误

                                        hideProgress()
                                        showToast("验证码错误或者已存在")
                                    }
                                } else {
                                    ViseLog.e("查询结果=====" + p1.toString())
                                }
                            }
                        })

                    } else {

                        hideProgress()
                        showToast("验证码错误")
                    }
                } else {
                    ViseLog.e("查询失败"+BmobUser.getCurrentUser(ManagerUser::class.java).orgId+"错误信息=="+p1.toString())
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        if (BmobUser.isLogin()) {
            logonOutLinear.visibility = View.VISIBLE
            logonOutText.visibility = View.VISIBLE
            userNameText.visibility = View.VISIBLE
            changePasswordText.visibility = View.VISIBLE

            when (BmobUser.getCurrentUser(ManagerUser::class.java).roleCode) {
                RoleCode.BOSS -> {//老板
                    getAllStaffText.visibility = View.VISIBLE
                    setInviteCode.visibility = View.VISIBLE
                    setManagerIdText.visibility = View.VISIBLE
                    getAllOrgText.visibility = View.VISIBLE
                    addDateLoveUser.visibility = View.GONE
                }
                RoleCode.EDITER -> {//录入者
                    setInviteCode.visibility = View.GONE
                    getAllStaffText.visibility = View.GONE
                    getAllOrgText.visibility = View.GONE
                    setManagerIdText.visibility = View.GONE
                    addDateLoveUser.visibility = View.VISIBLE

                }
            }
        } else {
            logonOutLinear.visibility = View.GONE
        }
    }

    fun findMyOrg() {
        if (BmobUser.isLogin()) {
            noLogin.visibility = View.GONE
            val user: ManagerUser? = BmobUser.getCurrentUser(ManagerUser::class.java)
            ViseLog.e("user.username==" + user!!.username)
            userNameText.text = user!!.username

            var query = BmobQuery<OrgBean>()
            query.addWhereEqualTo("objectId", user.orgId!!.toString())
            query.findObjects(object : FindListener<OrgBean>() {
                override fun done(p0: MutableList<OrgBean>?, p1: BmobException?) {
                    ViseLog.e("查询结果=====" + p0!![0].objectId)
                    userNameText.text = user.username.toString()
                    if (p1 == null) {
                        userNameText.text = user.username.toString() +
                                if (p0!![0].isOpen!!) {
                                    "机构已启用"
                                } else {
                                    "机构未启用"
                                }
                    } else {
                        ViseLog.e("查询失败" + p1.errorCode)
                        ViseLog.e("查询失败" + user.orgId!!.toString())
                    }
                }
            })
        } else {
            noLogin.visibility = View.VISIBLE

            noLogin.setButton("点击登录", {

                //跳转到下一级
                var intent = Intent(context, MineSwitchActivity::class.java)
                intent.putExtra(Flag.FragmentSwitch, FragmentName.FgLogin)
                startActivity(intent)

            })
        }

    }
}