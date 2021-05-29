package com.fcy.musicplayer.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Album(
    @PrimaryKey
    val albumId: String,
    val name: String,
    val poster: String,//封面图片地址
    val playNum: String,//播放量
    val list: List<Music>//专辑下的音乐列表
) {
}