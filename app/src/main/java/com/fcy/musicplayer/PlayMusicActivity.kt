package com.fcy.musicplayer

import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.helps.MediaPlayerHelp
import de.hdodenhof.circleimageview.CircleImageView
import jp.wasabeef.glide.transformations.BlurTransformation

class PlayMusicActivity : BaseActivity() {
    private lateinit var animator: ObjectAnimator
    private var mediaPlayerHelp: MediaPlayerHelp? = null
    private var rotationValue: Float = 0F
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_music)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Glide.with(this).load(R.drawable.test)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(findViewById(R.id.iv_background))
        playMusic(R.raw.music)
    }


    override fun onPause() {
        super.onPause()
        if (animator.isPaused) {
            animator.end()
            rotationValue = animator.animatedValue as Float
        }
    }

    public fun playMusic(path: String) {
        animator = initAnimator()
        animator.start()
        if (mediaPlayerHelp == null || !TextUtils.equals(mediaPlayerHelp?.path, path))
            mediaPlayerHelp = MediaPlayerHelp.instance.apply {
                setPath(path, this@PlayMusicActivity)
                start()
            }
    }

    public fun playMusic(id: Int) {
        animator = initAnimator()
        animator.start()
        if (mediaPlayerHelp == null || !TextUtils.equals(mediaPlayerHelp?.path, id.toString()))
            mediaPlayerHelp = MediaPlayerHelp.instance.apply {
                setPath(id, this@PlayMusicActivity)
                start()
            }
    }

    private fun pauseMusic() {
        if (animator.isPaused) {
            animator.end()
            rotationValue = animator.animatedValue as Float
        }
        mediaPlayerHelp?.pause()
    }

    private fun initAnimator(): ObjectAnimator =
        ObjectAnimator.ofFloat(
            findViewById<CircleImageView>(R.id.civ_album),
            "rotation",
            rotationValue,
            360F
        ).apply {
            interpolator = LinearInterpolator()
            duration = 20000
            repeatCount = -1
        }


}