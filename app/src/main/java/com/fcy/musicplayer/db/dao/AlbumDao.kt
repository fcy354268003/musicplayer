package com.fcy.musicplayer.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fcy.musicplayer.db.entity.Album

@Dao
interface AlbumDao {
    /**
     * 存储数据
     */
    @Insert
    fun saveAlbum(list: List<Album>)

    @Insert
    fun saveAlbum(album: Album)

    /**
     * 清空数据
     */
    @Query("DELETE FROM album WHERE 1=1")
    fun clearAll()

    @Query("SELECT * FROM Album ORDER BY playNum")
    fun getAll(): List<Album>

    @Query("SELECT * FROM Album WHERE albumId = :id")
    fun loadById(id: String): Album?

}