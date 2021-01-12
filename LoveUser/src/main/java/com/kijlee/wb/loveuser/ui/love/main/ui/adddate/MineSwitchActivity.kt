package com.kijlee.wb.loveuser.ui.love.main.ui.adddate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.flag.Flag
import com.kijlee.wb.loveuser.flag.FragmentName
import com.kijlee.wb.loveuser.ui.love.main.ui.org.Fg_AllOrg
import com.kijlee.wb.loveuser.ui.love.main.ui.org.Fg_AllStaff
import com.kijlee.wb.loveuser.ui.love.main.ui.setting.Fg_PasswordSetting

/**
 * 我的界面分发
 */
class MineSwitchActivity : AppCompatActivity() {
    var flag:String?  = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_norm)
        flag = intent.getStringExtra(Flag.FragmentSwitch)
        when(flag){
            FragmentName.FgAddLoveUser->{
                var addDateSm = FgAddLoveUser()
                var fm = supportFragmentManager!!.beginTransaction()
                var bundle=Bundle()
                addDateSm .arguments = bundle
                fm.replace(R.id.addFragment, addDateSm, flag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .addToBackStack(null)
                fm.commit()
            }
            FragmentName.FgRegister->{
                var fgRegister = FgRegister()
                var fm = supportFragmentManager!!.beginTransaction()
                var bundle=Bundle()
                bundle.putString(Flag.FragmentSwitch,flag)
                fgRegister .arguments = bundle
                fm.replace(R.id.addFragment, fgRegister, flag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fm.commit()
            }
            FragmentName.FgLogin->{
                var fgLogin = Fg_Login()
                var fm = supportFragmentManager!!.beginTransaction()
                var bundle=Bundle()
                bundle.putString(Flag.FragmentSwitch,flag)
                fgLogin .arguments = bundle
                fm.replace(R.id.addFragment, fgLogin, flag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fm.commit()
            }
            FragmentName.FgEditDateSm->{
                var fgEditDateSm = FgEditDateLoveUser()
                var fm = supportFragmentManager!!.beginTransaction()
                var bundle=Bundle()
                bundle.putString(Flag.FragmentSwitch,flag)
                bundle.putSerializable(Flag.ToNextBean,intent.getSerializableExtra(Flag.GetLastBean))
                fgEditDateSm .arguments = bundle
                fm.replace(R.id.addFragment, fgEditDateSm, flag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fm.commit()
            }
            FragmentName.FgAddOrg->{//注册机构
                var fgAddorg = Fg_OrgSign()
                var fm = supportFragmentManager!!.beginTransaction()
                fm.replace(R.id.addFragment, fgAddorg, flag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fm.commit()
            }
            FragmentName.Fg_AllOrg->{//全部机构
                var fgAllOrg = Fg_AllOrg()
                var fm = supportFragmentManager!!.beginTransaction()
                fm.replace(R.id.addFragment, fgAllOrg, flag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fm.commit()
            }
            FragmentName.Fg_PasswordSetting->{//修改密码
                var fgPasswordSetting = Fg_PasswordSetting()
                var fm = supportFragmentManager!!.beginTransaction()
                fm.replace(R.id.addFragment, fgPasswordSetting, flag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fm.commit()
            }

            FragmentName.Fg_AllStaff->{//全部员工
                var fgAllStaff = Fg_AllStaff()
                var fm = supportFragmentManager!!.beginTransaction()
                fm.replace(R.id.addFragment,fgAllStaff, flag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fm.commit()
            }
        }
    }


}