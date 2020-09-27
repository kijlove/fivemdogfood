package com.kijlee.wb.yszx.entity

import com.kijlee.wb.yszx.R
import com.kijlee.wb.yszx.ui.juhe.JuheActivity
import com.kijlee.wb.yszx.ui.showapi.ShowApiActivity
import com.kijlee.wb.yszx.ui.videos.VideosActivity
import com.kijlee.wb.yszx.ui.fivem.FiveMDogFoodActivity
import com.kijlee.wb.yszx.ui.phptest.PhpTestActivity
import java.io.Serializable

class MyApi constructor(nameRec: Int, activity: Class<*>?):
    Serializable {
    var nameRec: Int = 0
    var activity: Class<*>? = null
    init {
        this.nameRec = nameRec
        this.activity = activity
    }
    companion object{
        fun myApiList(): MutableList<MyApi> {
            var moduleList: MutableList<MyApi> = ArrayList<MyApi>()
            moduleList.add(MyApi(R.string.ShowApiActivity ,ShowApiActivity::class.java))
            moduleList.add(MyApi(R.string.JuheActivity ,JuheActivity::class.java))
            moduleList.add(MyApi(R.string.VideosActivity , VideosActivity::class.java))
            moduleList.add(MyApi(R.string.FiveMDogFoodActivity , FiveMDogFoodActivity::class.java))
            moduleList.add(MyApi(R.string.PhpTestActivity , PhpTestActivity::class.java))
            return moduleList
        }
    }
}