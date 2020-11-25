package com.kijlee.wb.loveuser.ui.love.main.ui.adddate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.flag.Flag
import com.kijlee.wb.loveuser.flag.FragmentName
import com.kijlee.wb.loveuser.ui.love.main.ui.org.Fg_AllOrg
import com.kijlee.wb.loveuser.ui.love.main.ui.setting.Fg_PasswordSetting

class MineSwitchActivity : AppCompatActivity() {
    var flag:String?  = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_norm)
        flag = intent.getStringExtra(Flag.FragmentSwitch)
        when(flag){
            FragmentName.FgAddDateSm->{
                var addDateSm = FgAddLoveUser()
                var fm = supportFragmentManager!!.beginTransaction()
                var bundle=Bundle()
                addDateSm .arguments = bundle
                fm.replace(R.id.addFragment, addDateSm, flag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .addToBackStack(null)
                fm.commit()
            }
            FragmentName.FgRegister,FragmentName.FgLogonIn->{
                var fgRegister = FgRegister()
                var fm = supportFragmentManager!!.beginTransaction()
                var bundle=Bundle()
                bundle.putString(Flag.FragmentSwitch,flag)
                fgRegister .arguments = bundle
                fm.replace(R.id.addFragment, fgRegister, flag)
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
                var fgAddorg = Fg_AddOrg()
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
            FragmentName.Fg_AddStaff->{//添加员工
                var fgAddStaff = Fg_AddStaff()
                var fm = supportFragmentManager!!.beginTransaction()
                fm.replace(R.id.addFragment,fgAddStaff, flag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fm.commit()
            }
        }
    }


}