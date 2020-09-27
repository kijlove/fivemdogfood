package com.kijlee.wb.yszx.ui.videos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.kijlee.wb.yszx.R
import com.kijlee.wb.yszx.base.BaseActivity
import com.kijlee.wb.yszx.ui.showapi.tnl.Fg_TnlStyle

/**
 * 视频测试
 */
class VideosActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_norm)
        var fgVideosTest = Fg_VideosTest()
        var fm = supportFragmentManager!!.beginTransaction()
        fm.replace(R.id.addFragment, fgVideosTest, "Fg_VideosTest")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
        fm.commit()
    }
}