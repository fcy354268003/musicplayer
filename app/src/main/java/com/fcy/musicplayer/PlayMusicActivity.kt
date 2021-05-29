package com.fcy.musicplayer

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.db.entity.Music
import com.fcy.musicplayer.helps.MediaPlayerHelp
import com.fcy.musicplayer.repository.LocalHelper
import com.fcy.musicplayer.util.LoggerUtil
import jp.wasabeef.glide.transformations.BlurTransformation

class PlayMusicActivity : BaseActivity() {
    private lateinit var animator: ObjectAnimator
    private var mediaPlayerHelp: MediaPlayerHelp? = null
    private var isPlaying: Boolean = true

    private var id: String? = null
    private var info: Music? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_music)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        playMusic(info)
    }

    override fun onContentChanged() {
        super.onContentChanged()
        animator =
            ObjectAnimator.ofFloat(findViewById(R.id.civ_album), "rotation", 0F, 360F).apply {
                duration = 20000
                repeatCount = -1
                start()
            }
        Glide.with(this)
            .load(info?.poster)
            .placeholder(R.drawable.test)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 10)))
            .into(findViewById(R.id.iv_background))
        Glide.with(this)
            .load(info?.poster)
            .placeholder(R.drawable.test)
            .into(findViewById(R.id.civ_album))
        findViewById<TextView>(R.id.tv_musicName).text = info?.name
        findViewById<TextView>(R.id.tv_maker).text = info?.author
    }

    override fun initArgs() {
        super.initArgs()
        intent.extras?.apply {
            id = getString("id")
        }
        info = LocalHelper.instance.loadMusicById(id ?: "")
        LoggerUtil.d("$info $id")
    }

    override fun onPause() {
        super.onPause()
        animator.pause()
    }

    private fun playMusic(music: Music?) {
        mediaPlayerHelp = MediaPlayerHelp.instance.apply {
            setMusic(music, this@PlayMusicActivity)
            start()
        }
    }

    /**
     * 继续播放
     */
    private fun continuePlay() {
        animator.resume()
        mediaPlayerHelp?.continuePlay()
    }

    /**
     * 暂停播放
     */
    private fun pauseMusic() {
        animator.pause()
        mediaPlayerHelp?.pause()
    }

    override fun onStart() {
        super.onStart()
        animator.resume()
    }

    fun onAlbumClick(v: View) {
        if (isPlaying) {
            pauseMusic()

        } else continuePlay()
        isPlaying = !isPlaying
    }


}