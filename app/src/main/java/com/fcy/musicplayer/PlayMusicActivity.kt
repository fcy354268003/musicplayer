package com.fcy.musicplayer

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.db.entity.Music
import com.fcy.musicplayer.helps.MediaPlayerHelp
import com.fcy.musicplayer.repository.LocalHelper
import com.fcy.musicplayer.util.LoggerUtil
import de.hdodenhof.circleimageview.CircleImageView
import jp.wasabeef.glide.transformations.BlurTransformation

class PlayMusicActivity : BaseActivity() {
    private lateinit var animator: ObjectAnimator
    private var mediaPlayerHelp: MediaPlayerHelp? = null
    private var rotationValue: Float = 0F
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
        animator = ObjectAnimator.ofFloat(findViewById(R.id.civ_album), "rotation", 0F, 360F)
        animator.setDuration(20000)
        animator.repeatCount = -1
        animator.start()
        Glide.with(this)
            .load(info?.poster)
            .placeholder(R.drawable.test)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(findViewById(R.id.iv_background))
//        playMusic(R.raw.music)
        playMusic(info?.path)
        Glide.with(this)
            .load(info?.poster)
            .placeholder(R.drawable.test)
            .into(findViewById(R.id.civ_album))
        findViewById<TextView>(R.id.tv_musicName).text = info?.name
        findViewById<TextView>(R.id.tv_maker).text = info?.author
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        initArgs()
        playMusic(info?.path)
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

    fun playMusic(path: String?) {
        if (mediaPlayerHelp == null || !TextUtils.equals(mediaPlayerHelp?.path, path))
            mediaPlayerHelp = MediaPlayerHelp.instance.apply {
                setPath(path, this@PlayMusicActivity)
                start()
            }
    }

    /**
     * 播放本地资源
     */
    fun playMusic(id: Int) {
        animator.start()
        if (mediaPlayerHelp == null || !TextUtils.equals(mediaPlayerHelp?.path, id.toString()))
            mediaPlayerHelp = MediaPlayerHelp.instance.apply {
                setPath(id, this@PlayMusicActivity)
                start()
            }
    }

    private fun continuePlay() {
        animator.resume()
        mediaPlayerHelp?.continuePlay()
    }

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