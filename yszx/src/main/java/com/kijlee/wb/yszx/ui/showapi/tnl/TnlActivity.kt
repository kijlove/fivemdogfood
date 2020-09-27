package com.kijlee.wb.yszx.ui.showapi.tnl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.kijlee.wb.yszx.R
import com.kijlee.wb.yszx.base.BaseActivity

/**
 * 淘女郎
 */
class TnlActivity : BaseActivity() ,ToInfo{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_norm)
        var fgTnlstyle = Fg_TnlStyle()
        var fm = supportFragmentManager!!.beginTransaction()
        var bundle=Bundle()
        bundle.putSerializable("TnlStyleBean",intent.getSerializableExtra("TnlStyleBean"))
        fgTnlstyle .arguments = bundle
        fgTnlstyle .toInfo = this
        fm.replace(R.id.addFragment, fgTnlstyle, "Fg_TnlStyle")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
        fm.commit()
    }

    override fun toInfo(bundle: Bundle) {


        var fgTnlInfo = Fg_TnlInfo()
        var fm = supportFragmentManager!!.beginTransaction()
        fgTnlInfo .arguments = bundle
        fm.replace(
            R.id.addFragment, fgTnlInfo,
            "Fg_TnlInfo").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
        fm.commit()
    }
}