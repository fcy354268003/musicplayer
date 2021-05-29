package com.fcy.musicplayer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fcy.musicplayer.db.converter.AlbumConverter
import com.fcy.musicplayer.db.dao.AlbumDao
import com.fcy.musicplayer.db.dao.MusicDao
import com.fcy.musicplayer.db.dao.UserDao
import com.fcy.musicplayer.db.entity.Album
import com.fcy.musicplayer.db.entity.Music
import com.fcy.musicplayer.db.entity.User

@Database(entities = [User::class,Album::class,Music::class], version = 2)
@TypeConverters(AlbumConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun albumDao():AlbumDao
    abstract fun musicDao():MusicDao
}