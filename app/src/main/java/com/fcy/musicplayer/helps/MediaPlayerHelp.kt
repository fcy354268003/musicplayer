package com.fcy.musicplayer.helps

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.fcy.musicplayer.db.entity.Music

/**
 * 全局单例播放器
 */
class MediaPlayerHelp private constructor() {
    private lateinit var context: Context
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private val prepareCallback: MediaPlayer.OnPreparedListener? = null
    var music: Music? = null
        private set

    companion object {
        @JvmStatic
        val instance: MediaPlayerHelp by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            MediaPlayerHelp()
        }
    }

    fun start() {
        if (mediaPlayer.isPlaying)
            return
        mediaPlayer.start()
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun continuePlay() {
        mediaPlayer.start()
    }

    fun setMusic(music: Music?, context: Context) {
        if (music == null || this.music == music)
            return

        mediaPlayer.stop()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()

        this.music = music
        mediaPlayer.setDataSource(context, Uri.parse(music.path))
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener { mediaPlayer.start() }
    }

    fun setMusic(id: Int, context: Context) {
        if (mediaPlayer.isPlaying)
            mediaPlayer.reset()
        this.music = null
        mediaPlayer = MediaPlayer.create(context, id)
        mediaPlayer.start()
    }

}