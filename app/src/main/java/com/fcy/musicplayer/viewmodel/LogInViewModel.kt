package com.fcy.musicplayer.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fcy.musicplayer.util.InputCheckUtil
import com.fcy.musicplayer.util.LoggerUtil

/**
 * 登陆界面
 */
class LogInViewModel : ViewModel() {
    val phone: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    fun checkPhone(): Pair<String, Int> =
        when (InputCheckUtil.isPhoneNumberStandard(phone.value.toString())) {
            0 -> Pair(
                "请先填写完整", 0
            )
            1
            -> Pair(
                "手机号不符合规范", 1
            )
            else -> Pair(
                "登录成功", 2
            )
        }


}