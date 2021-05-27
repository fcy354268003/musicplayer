@file:JvmName("InputCheckUtil")

package com.fcy.musicplayer.util

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

/**
 * 检查phoneNumber是否符合规范
 * @return 0 ：为空 1 : 不符合规范  2 ： 符合规范
 */
class InputCheckUtil {
    companion object {
        fun isPhoneNumberStandard(text: String?): Int {
            if (LoggerUtil.DEBUG)
                return 2
            LoggerUtil.d("$text  ========")
            if (text == null)
                return 0
            val replace = text.replace(" ", "")
            if (replace.isEmpty())
                return 0
            if (replace.length != 11 || replace[0] != '1')
                return 1
            return 2
        }

        fun register(context: Context, phone: String, pass1: String, pass2: String): Boolean {
            if (isPhoneNumberStandard(phone) != 2) {
                Toast.makeText(context, "手机号不符合规范", Toast.LENGTH_SHORT).show()
                return false
            }
            if (!TextUtils.equals(pass1, pass2)) {
                Toast.makeText(context, "两次密码输入不一致", Toast.LENGTH_SHORT).show()
                return false
            }
            // TODO 检查是否已存在该用户

            Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show()
            return true
        }
    }
}