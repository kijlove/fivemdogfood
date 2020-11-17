package com.kijlee.wb.loveuser.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.Option
import com.google.android.material.snackbar.Snackbar
import com.kaopiz.kprogresshud.KProgressHUD
import com.qmuiteam.qmui.widget.QMUIProgressBar

/**
 * 基类
 */
abstract class BaseFragment : Fragment(), BaseView {
    var hub: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hub = KProgressHUD(context)
    }

    override fun showProgress() {
        hub!!.show()
    }

    override fun hideProgress() {
        hub!!.dismiss()
    }

    override fun showMsg(message: String) {
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnack(viewLayout: View, message: String) {
        Snackbar.make(
                viewLayout!!,
                message,
                Snackbar.LENGTH_SHORT
        ).setAction("关闭", { view ->

        }).show()
    }

}