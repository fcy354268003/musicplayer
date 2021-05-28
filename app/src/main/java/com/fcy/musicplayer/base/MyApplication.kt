package com.fcy.musicplayer.base

import android.app.Application
import androidx.room.Room
import com.fcy.musicplayer.db.AppDatabase
import com.fcy.musicplayer.db.dao.UserDao

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
    }
}