package com.kijlee.wb.fivemdogfood.ui.phptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.ui.juhe.Fg_CarBrandList

class PhpTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_norm)
        var fgPhpTest = Fg_PhpTest()
        var fm = supportFragmentManager!!.beginTransaction()
        fm.replace(R.id.addFragment, fgPhpTest, "Fg_PhpTest")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
        fm.commit()
    }
}