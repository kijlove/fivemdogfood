package com.kijlee.wb.fivemdogfood.ui.fivem.main.ui.adddate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.flag.Flag
import com.kijlee.wb.fivemdogfood.flag.FragmentName
import com.kijlee.wb.fivemdogfood.ui.showapi.tnl.Fg_TnlStyle

class FiveMAddDateActivity : AppCompatActivity() {
    var flag:String?  = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_norm)
        flag = intent.getStringExtra(Flag.FragmentSwitch)
        when(flag){
            FragmentName.FgAddDateSm->{
                var addDateSm = FgAddDateSm()
                var fm = supportFragmentManager!!.beginTransaction()
                var bundle=Bundle()
                addDateSm .arguments = bundle
                fm.replace(R.id.addFragment, addDateSm, "FgAddDateSm")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
                fm.commit()
            }
            FragmentName.FgRegister->{
                var fgRegister = FgRegister()
                var fm = supportFragmentManager!!.beginTransaction()
                var bundle=Bundle()
                fgRegister .arguments = bundle
                fm.replace(R.id.addFragment, fgRegister, "FgRegister")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
                fm.commit()
            }
        }
    }
}