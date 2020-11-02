package com.kijlee.wb.loveuser.ui.juhe

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.base.BaseActivity
import com.kijlee.wb.loveuser.ui.juhe.interfaces.CarInterface

class JuheActivity : BaseActivity(), CarInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_norm)
        var fgCarBrandList = Fg_CarBrandList()
        fgCarBrandList.carInterface = this
        var fm = supportFragmentManager!!.beginTransaction()
        fm.replace(R.id.addFragment, fgCarBrandList, "Fg_CarBrandList")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
        fm.commit()
    }

    override fun toCarSeries(bundle: Bundle) {

        var fgCarSeriesList = Fg_CarSeriesList()
        fgCarSeriesList.arguments = bundle
        fgCarSeriesList.carInterface = this
        var fm = supportFragmentManager!!.beginTransaction()
        fm.replace(R.id.addFragment, fgCarSeriesList, "Fg_CarSeriesList")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
        fm.commit()

    }

    override fun toCarModels(bundle: Bundle) {

        var fgCarModelsList = Fg_CarModelsList()
        fgCarModelsList.arguments = bundle
        var fm = supportFragmentManager!!.beginTransaction()
        fm.replace(R.id.addFragment, fgCarModelsList, "Fg_CarModelsList")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
        fm.commit()

    }

}