package com.fcy.musicplayer.helps

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.fcy.musicplayer.LogInActivity
import com.fcy.musicplayer.MainActivity
import com.fcy.musicplayer.repository.LocalHelper
import com.fcy.musicplayer.util.InputCheckUtil
import com.fcy.musicplayer.util.LoggerUtil
import com.fcy.musicplayer.util.SPUtil
import com.fcy.musicplayer.viewmodel.LogInViewModel
import com.fcy.musicplayer.widget.InputView

class UserHelp private constructor() {
    private var phone: String? = null

    companion object {
        @JvmStatic
        val instance: UserHelp by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            UserHelp()
        }
    }

    /**
     * 验证是否已经登陆
     */
    fun validateLogin(context: Context): Boolean {
        return SPUtil.isLoginUser(context)
    }

    /**
     * 登录
     *  1.验证信息
     *      1.成功的话就保存登录信息到sp
     *      2.登陆失败的话给出提示
     */
    fun login(context: Context, viewModel: LogInViewModel): Boolean {
        val result = viewModel.checkPhone()

        LoggerUtil.d("${result.second} ${result.first}")
        if (result.second != 2) {
            Toast.makeText(context, result.first, Toast.LENGTH_SHORT).show()
            return false
        }

        //TODO 通过数据库查询用户状态
        if (LocalHelper.instance.validUserInfo(viewModel)) {
            SPUtil.saveUserInfo(context, viewModel.phone.value!!)
            Toast.makeText(context, result.first, Toast.LENGTH_SHORT).show()
            return true
        } else {
            Toast.makeText(context, "用户名或者密码错误", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    /**
     * 退出登录
     *  1.跳转到登陆界面，清空task栈
     *  2.清除sp登录信息
     */
    fun logout(context: Context): Boolean {
        if (!SPUtil.deleteLoginInfo(context)) {
            Toast.makeText(context, "发生错误", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    /**
     * 用户注册
     * 1.检查是否符合规范
     * 2.用户是否已经存在
     */
    fun register(phone: String, pw1: String, pw2: String): Boolean {
        if (TextUtils.isEmpty(phone) or
            TextUtils.isEmpty(pw1) or
            TextUtils.isEmpty(pw2) or
            (InputCheckUtil.isPhoneNumberStandard(phone) != 2) or // 检查手机号是否规范
            !TextUtils.equals(pw1, pw2) or   //检查两次密码是否一致
            LocalHelper.instance.isUserExist(phone) // 检查用户是否已经存在
        )
            return false
        LocalHelper.instance.insertUser(phone, pw1)
        return true

    }

    private fun toLogIn() {

    }


}