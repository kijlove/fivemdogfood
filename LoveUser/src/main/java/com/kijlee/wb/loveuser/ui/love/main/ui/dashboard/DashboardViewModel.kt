package com.kijlee.wb.loveuser.ui.love.main.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "使用说明,该项目已开源，禁止用于违法乱纪等行为"
    }
    val text: LiveData<String> = _text
}