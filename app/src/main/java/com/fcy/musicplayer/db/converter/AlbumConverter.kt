package com.fcy.musicplayer.db.converter

import androidx.room.TypeConverter
import com.fcy.musicplayer.db.entity.Music
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AlbumConverter {
    @TypeConverter
    fun objToString(list: List<Music>): String = Gson().toJson(list)

    @TypeConverter
    fun stringToObj(value:String): List<Music> {
        val listType = object : TypeToken<List<Music>>(){}.type
        return Gson().fromJson(value, listType)
    }
}