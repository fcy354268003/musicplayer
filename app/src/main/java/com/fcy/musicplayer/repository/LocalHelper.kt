package com.fcy.musicplayer.repository

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.fcy.musicplayer.db.AppDatabase
import com.fcy.musicplayer.db.dao.AlbumDao
import com.fcy.musicplayer.db.dao.MusicDao
import com.fcy.musicplayer.db.dao.UserDao
import com.fcy.musicplayer.db.entity.Album
import com.fcy.musicplayer.db.entity.Music
import com.fcy.musicplayer.db.entity.MusicSource
import com.fcy.musicplayer.db.entity.User
import com.fcy.musicplayer.helps.ApplicationHelper
import com.fcy.musicplayer.util.LiveDataManager
import com.fcy.musicplayer.util.LoggerUtil
import com.fcy.musicplayer.viewmodel.LogInViewModel
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.random.Random

class LocalHelper private constructor() {
    private val gson: Gson = Gson()

    private val userDao: UserDao
    private val musicDao: MusicDao
    private val albumDao: AlbumDao
    private val threadPool: ExecutorService = Executors.newCachedThreadPool()

    init {
        Room.databaseBuilder(
            ApplicationHelper.getApplicationByReflect(),
            AppDatabase::class.java,
            "database-music"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build().apply {
            userDao = userDao()
            musicDao = musicDao()
            albumDao = albumDao()
        }
    }

    companion object {
        @JvmStatic
        val instance: LocalHelper by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            LocalHelper()
        }

    }

    /**
     * 本地数据库是否有用户信息
     */
    fun isUserExist(phone: String): Boolean {
        val loadById = userDao.loadById(phone)
        return loadById != null
    }

    /**
     * 验证用户信息是否正确
     */
    fun validUserInfo(userInfo: LogInViewModel): Boolean {
        if (TextUtils.isEmpty(userInfo.phone.value) or TextUtils.isEmpty(userInfo.password.value))
            return false
        val loadById = userDao.loadById(phon = userInfo.phone.value!!)
        if (loadById?.pwd == userInfo.password.value) {
            return true
        }
        return false
    }

    /**
     * 将用户信息注册到本地数据库
     */
    fun insertUser(phone: String, pwd: String) {
        userDao.insertUser(
            User(phone, pwd)
        )
    }

    /**
     * 将json数据转化为对象并存储在数据库
     */
    fun saveMusicData(context: Context) {
        threadPool.execute {
            val sb = StringBuilder()
            context.assets?.open(PATH_MUSIC_DATA)?.use {
                BufferedReader(InputStreamReader(it)).apply {
                    var cur: String? = String()
                    cur = readLine()
                    while (cur != null) {
                        sb.append(cur)
                        cur = readLine()
                    }
                }
            }
            val musicSource = gson.fromJson(sb.toString(), MusicSource::class.java)
            musicDao.saveMusic(musicSource.hot)
            albumDao.saveAlbum(musicSource.album)
        }

    }

    /**
     * 清除数据库中的数据
     */
    fun deleteMusicData() {
        threadPool.execute {
            musicDao.clearAll()
            albumDao.clearAll()
        }
    }

    /**
     * 异步查询专辑信息通过LiveData，回调显示数据
     */
    fun loadAlbumAsync() {
        threadPool.execute {
            val with: MutableLiveData<List<Album>> =
                LiveDataManager.with<List<Album>>("album") as MutableLiveData<List<Album>>
            with.postValue(albumDao.getAll())
        }
    }

    /**
     * 异步查询歌曲信息通过LiveData，回调显示数据
     */
    fun loadMusicAsync() {
        threadPool.execute {
            val with: MutableLiveData<List<Music>> =
                LiveDataManager.with<List<Music>>("music") as MutableLiveData<List<Music>>
            with.postValue(musicDao.load20())
        }
    }

    fun getMusicIdRandom(): String {
        val loadRandomId = musicDao.loadRandomId()
        val index = Random.nextInt(loadRandomId.size)
        LoggerUtil.d("$index \t  ${loadRandomId[index]}")
        return loadRandomId[index]
    }

    fun loadMusicById(id: String): Music? = musicDao.loadById(id)

    fun loadAlbumById(albumId: String): Album? =
        albumDao.loadById(albumId)

}

private const val PATH_MUSIC_DATA = "DataSource.json"