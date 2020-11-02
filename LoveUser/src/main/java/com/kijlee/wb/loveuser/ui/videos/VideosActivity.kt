package com.kijlee.wb.loveuser.ui.videos

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.base.BaseActivity

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