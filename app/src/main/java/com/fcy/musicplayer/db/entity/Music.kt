package com.fcy.musicplayer.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Music(
    @PrimaryKey
    val musicId: String = "",
    val name: String = "",
    val poster: String = "",
    val path: String = "",//mp3资源路径
    val author: String = ""
)