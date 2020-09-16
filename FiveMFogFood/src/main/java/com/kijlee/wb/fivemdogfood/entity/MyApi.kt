package com.kijlee.wb.fivemdogfood.entity

import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.ui.juhe.JuheActivity
import com.kijlee.wb.fivemdogfood.ui.showapi.ShowApiActivity
import com.kijlee.wb.fivemdogfood.ui.videos.VideosActivity
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
            return moduleList
        }
    }
}