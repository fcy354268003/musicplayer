package com.fcy.musicplayer.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val phone: String,
    @ColumnInfo val pwd: String
)