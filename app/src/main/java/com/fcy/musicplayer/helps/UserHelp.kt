package com.fcy.musicplayer.helps

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.fcy.musicplayer.LogInActivity
import com.fcy.musicplayer.MainActivity
import com.fcy.musicplayer.util.LoggerUtil
import com.fcy.musicplayer.util.SPUtil
import com.fcy.musicplayer.viewmodel.LogInViewModel

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
    fun login(context: Context, viewModel: LogInViewModel) {

        val result = viewModel.checkPhone()
        Toast.makeText(context, result.first, Toast.LENGTH_SHORT).show()
        LoggerUtil.d(result.second)
        if (result.second != 2) {
            return
        }


        //TODO 通过数据库查询用户状态

        toMain(context)

    }

    /**
     * 退出登录
     *  1.跳转到登陆界面，清空task栈
     *  2.清除sp登录信息
     */
    fun logout(context: Context) {
        if (!SPUtil.deleteLoginInfo(context)) {
            Toast.makeText(context, "发生错误", Toast.LENGTH_SHORT).show()
        }
        Intent(context, LogInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(this)
        }
    }

    private fun toMain(context: Context) {
        val intent = Intent(context, LogInActivity::class.java)
        context.startActivity(intent)
    }
}