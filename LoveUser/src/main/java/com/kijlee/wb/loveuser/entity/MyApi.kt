package com.kijlee.wb.loveuser.entity

import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.ui.juhe.JuheActivity
import com.kijlee.wb.loveuser.ui.showapi.ShowApiActivity
import com.kijlee.wb.loveuser.ui.videos.VideosActivity
import com.kijlee.wb.loveuser.ui.love.LoveUserActivity
import com.kijlee.wb.loveuser.ui.phptest.PhpTestActivity
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
            moduleList.add(MyApi(R.string.FiveMDogFoodActivity , LoveUserActivity::class.java))
            moduleList.add(MyApi(R.string.PhpTestActivity , PhpTestActivity::class.java))
            return moduleList
        }
    }
}