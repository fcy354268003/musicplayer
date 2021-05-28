package com.fcy.musicplayer.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fcy.musicplayer.db.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * From user WHERE phone = :phon")
    fun loadById(phon: String): User?

    @Insert
    fun insertUser(vararg user: User)

    @Delete
    fun deleteUser(user: User)
}