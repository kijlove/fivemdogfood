package com.kijlee.wb.fivemdogfood.ui.fivem

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.base.BaseActivity
import com.mob.MobSDK
import com.mob.OperationCallback

/**
 * 5M狗粮项目
 */
class FiveMDogFoodActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fivem_index)
        MobSDK.submitPolicyGrantResult(true,null)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.fg_mine
            )
        )

        setupActionBarWithNavController(navController!!, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
//
//var fgAddFiveMDogFoodUser = Fg_AddFiveMDogFoodUser()
//    var fm = supportFragmentManager!!.beginTransaction()
//    fm.replace(R.id.addFragment, fgAddFiveMDogFoodUser, "Fg_AddFiveMDogFoodUser")
//    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
//    fm.commit()
}