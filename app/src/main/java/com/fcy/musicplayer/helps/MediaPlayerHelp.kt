package com.fcy.musicplayer.helps

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

/**
 * 全局单例播放器
 */
class MediaPlayerHelp private constructor() {
    private lateinit var context: Context
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private val prepareCallback: MediaPlayer.OnPreparedListener? = null
    var path: String? = null
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

    fun setPath(path: String?, context: Context) {
        if (path == null)
            return
        if (this.path == path) {
            mediaPlayer.reset()
        } else {
            mediaPlayer = MediaPlayer()
        }
        this.path = path
        mediaPlayer.setDataSource(context, Uri.parse(path))
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener { mediaPlayer.start() }
    }

    public fun setPath(id: Int, context: Context) {
        if (mediaPlayer.isPlaying)
            mediaPlayer.reset()
        this.path = id.toString()
        mediaPlayer = MediaPlayer.create(context, id)
        mediaPlayer.start()
    }

}