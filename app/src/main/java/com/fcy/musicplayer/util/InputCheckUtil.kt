@file:JvmName("InputCheckUtil")

package com.fcy.musicplayer.util

/**
 * 检查phoneNumber是否符合规范
 * @return 0 ：为空 1 : 不符合规范  2 ： 符合规范
 */
class InputCheckUtil {
    companion object {
        fun isPhoneNumberStandard(text: String?): Int {
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
    }
}