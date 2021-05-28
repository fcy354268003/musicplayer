package com.fcy.musicplayer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fcy.musicplayer.db.dao.UserDao
import com.fcy.musicplayer.db.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}