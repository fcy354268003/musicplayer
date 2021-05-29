package com.fcy.musicplayer.db.entity

import androidx.room.Entity

@Entity
data class MusicSource(
    val album: List<Album>,
    val hot: List<Music>,
) {
}