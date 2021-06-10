package com.fcy.musicplayer.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fcy.musicplayer.db.entity.Music
import com.fcy.musicplayer.db.entity.User

@Dao
interface MusicDao {
    /**
     * 存储音乐
     */
    @Insert
    fun saveMusic(list: List<Music>)

    @Insert
    fun saveMusic(music: Music)


    /**
     * 清空数据
     */
    @Query("DELETE FROM music where 1=1")
    fun clearAll()

    @Query("SELECT * FROM music Limit 20")
    fun load20(): List<Music>

    @Query("SELECT * FROM music WHERE musicId = :id")
    fun loadById(id: String): Music?

    @Query("SELECT musicId FROM music")
    fun loadRandomId():List<String>

}