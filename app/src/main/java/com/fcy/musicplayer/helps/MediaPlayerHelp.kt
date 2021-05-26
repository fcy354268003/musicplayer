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

    public fun start() {
        if (mediaPlayer.isPlaying)
            return
        mediaPlayer.start()
    }

    public fun pause() {
        mediaPlayer.pause()
    }

    public fun setPath(path: String, context: Context) {
        if (mediaPlayer.isPlaying)
            mediaPlayer.reset()
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