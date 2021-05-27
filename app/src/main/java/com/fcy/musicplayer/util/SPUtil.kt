package com.fcy.musicplayer.util

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

class SPUtil {

    companion object {
        private val SP_NAME_USER = "user"
        private val SP_KEY_PHONE = "phone"
        fun saveUserInfo(context: Context, phone: String): Boolean {
            context.getSharedPreferences(SP_NAME_USER, Context.MODE_PRIVATE).edit().apply {
                this?.putString(SP_KEY_PHONE, phone)
                return this?.commit() ?: false
            }
        }

        /**
         * 验证当前是否已经有登录用户
         */
        fun isLoginUser(context: Context): Boolean {
            context.getSharedPreferences(SP_NAME_USER, Context.MODE_PRIVATE).apply {
                getString(SP_KEY_PHONE, "").also {
                    if (TextUtils.isEmpty(it)) {
                        return false
                    }
                    return true
                }
            }
        }

        /**
         * 清除登录信息
         */
        fun deleteLoginInfo(context: Context): Boolean {
            context.getSharedPreferences(SP_NAME_USER, Context.MODE_PRIVATE).edit().apply {
                this?.putString(SP_KEY_PHONE, "")
                return this?.commit() ?: false

            }
        }
    }
}