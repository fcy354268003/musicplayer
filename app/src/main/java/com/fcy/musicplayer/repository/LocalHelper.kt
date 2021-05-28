package com.fcy.musicplayer.repository

import android.content.Context
import android.text.TextUtils
import androidx.room.Room
import com.fcy.musicplayer.base.MyApplication
import com.fcy.musicplayer.db.AppDatabase
import com.fcy.musicplayer.db.dao.UserDao
import com.fcy.musicplayer.db.entity.User
import com.fcy.musicplayer.helps.ApplicationHelper
import com.fcy.musicplayer.viewmodel.LogInViewModel

class LocalHelper private constructor() {
    private val userDao: UserDao by lazy {
        Room.databaseBuilder(
            ApplicationHelper.getApplicationByReflect(),
            AppDatabase::class.java,
            "database-music"
        ).allowMainThreadQueries().build().userDao()
    }

    companion object {
        @JvmStatic
        val instance: LocalHelper by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            LocalHelper()
        }
    }

    /**
     * 本地数据库是否有用户信息
     */
    fun isUserExist(phone: String): Boolean {
        val loadById = userDao.loadById(phone)
        return loadById != null
    }

    /**
     * 验证用户信息是否正确
     */
    fun validUserInfo(userInfo: LogInViewModel): Boolean {
        if (TextUtils.isEmpty(userInfo.phone.value) or TextUtils.isEmpty(userInfo.password.value))
            return false
        val loadById = userDao.loadById(phon = userInfo.phone.value!!)
        if (loadById?.pwd == userInfo.password.value)
            return true
        return false
    }

    /**
     * 将用户信息注册到本地数据库
     */
    fun insertUser(phone: String, pwd: String) {
        userDao.insertUser(
            User(phone, pwd)
        )
    }
}