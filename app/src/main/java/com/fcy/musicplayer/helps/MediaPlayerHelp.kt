package com.fcy.musicplayer.helps

import android.animation.ObjectAnimator
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.lifecycle.*
import com.fcy.musicplayer.databinding.ActivityPlayMusicBinding
import com.fcy.musicplayer.db.entity.Music
import com.fcy.musicplayer.repository.LocalHelper
import com.fcy.musicplayer.util.LoggerUtil
import java.util.*

/**
 * 全局单例播放器
 */
class MediaPlayerHelp private constructor() : LifecycleObserver {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private val handler: Handler = Handler()
    val isPlaying: MutableLiveData<Boolean> = MutableLiveData()
    private var binding: ActivityPlayMusicBinding? = null
    private var animator: ObjectAnimator? = null
    val process: MutableLiveData<Int> = MutableLiveData()
    val liveMusic: MutableLiveData<Music> = MutableLiveData()
    var list: MusicList? = null

    //当前播放音乐
    private val delay: Long = 300L // 刷新进度条的时间间隔

    companion object {
        @JvmStatic
        val instance: MediaPlayerHelp by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            MediaPlayerHelp()
        }
    }

    private val action: Runnable = object : Runnable {
        override fun run() {
            if (mediaPlayer.currentPosition != 0) {
                val duration = mediaPlayer.duration
                val currentPosition = mediaPlayer.currentPosition
                process.value = currentPosition * 10000 / duration
                if (mediaPlayer.isPlaying) {
                    handler.postDelayed(this, delay)
                }
            }
        }
    }

    /**
     * 使用handler定时的向LiveData写数据
     */
    private fun startLoop() {
        handler.postDelayed(action, delay)
    }

    /**
     * 所有开始相关 都要放在这个方法
     */
    fun start() {
        if (mediaPlayer.isPlaying)
            return
        mediaPlayer.start()
        isPlaying.value = true
        startLoop()
        if (animator?.isPaused == true) {
            animator?.resume()
        } else animator?.start()
    }

    /**
     * 所有暂停相关都要调用 这个方法
     */
    fun pause() {
        mediaPlayer.pause()
        isPlaying.value = false
        animator?.pause()
    }

    private fun continuePlay() {
        start()
    }

    /**
     * 光盘点击事件
     */
    fun onAlbumClick(v: View?) {
        if (mediaPlayer.isPlaying) {
            pause()
        } else continuePlay()
    }

    /**
     *播放网络音乐
     */
    fun setMusic(music: Music?, context: Context, binding: ActivityPlayMusicBinding?) {
        this.binding = binding
        if (music == null || this.liveMusic.value == music)
            return
        mediaPlayer.stop()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()
        LoggerUtil.d(music.musicId)
        liveMusic.value = music
        val ele = MusicList(music.musicId)
        if (list == null) {
            list = ele
        } else {
            list?.nextNode = ele
            ele.preNode = list
            list = ele
        }

        mediaPlayer.setDataSource(context, Uri.parse(music.path))
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener { start() }
    }

    /**
     * 播放本地资源音乐
     */
    fun setMusic(id: Int, context: Context) {
        if (mediaPlayer.isPlaying)
            mediaPlayer.reset()
        this.liveMusic.value = null
        mediaPlayer = MediaPlayer.create(context, id)
        start()
    }

    /**
     * 暂停动画旋转
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onActivityPause() {
        animator?.pause()
        LoggerUtil.d("onPause")
    }

    /**
     * 继续动画旋转
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onActivityResume() {
        LoggerUtil.d("===Resume${mediaPlayer.isPlaying}")
        if (mediaPlayer.isPlaying) {
            LoggerUtil.d("==play ${animator?.isStarted}")
            if (animator != null && !(animator!!.isStarted)) {
                animator!!.start()
            } else
                animator?.resume()
            LoggerUtil.d("$animator")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onActivityCreate() {
        LoggerUtil.d("onActivityCreate")
        animator = ObjectAnimator.ofFloat(binding?.civAlbum, "rotation", 0F, 360F).apply {
            duration = 20000
            repeatCount = -1
            interpolator = LinearInterpolator()
        }
    }

    /**
     * 清除view相关的绑定，避免内存泄漏
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onActivityDestroy() {
        binding = null
        animator = null
    }

    fun nextMusic(): String {
        return list?.nextNode?.id ?: LocalHelper.instance.getMusicIdRandom()
    }

    fun preMusic(): String? {
        return list?.preNode?.id
    }

    inner class MusicList(val id: String) {
        var preNode: MusicList? = null
        var nextNode: MusicList? = null
    }

}