package com.fcy.musicplayer

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.databinding.ActivityPlayMusicBinding
import com.fcy.musicplayer.db.entity.Music
import com.fcy.musicplayer.helps.MediaPlayerHelp
import com.fcy.musicplayer.repository.LocalHelper
import com.fcy.musicplayer.util.BarUtils
import com.fcy.musicplayer.util.LoggerUtil
import jp.wasabeef.glide.transformations.BlurTransformation

class PlayMusicActivity : BaseActivity() {
    private lateinit var mediaPlayerHelp: MediaPlayerHelp
    private var id: String? = null
    private var info: Music? = null
    private lateinit var binding: ActivityPlayMusicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        BarUtils.setStatusBarLightMode(this, true)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_play_music
        )
        playMusic(info)
        onContentInit()


        binding.civAlbum.setOnClickListener(mediaPlayerHelp::onAlbumClick)
        lifecycle.addObserver(MediaPlayerHelp.instance)
        binding.musicHelp = mediaPlayerHelp
        binding.mrInner.apply {
            preCallback = {

            }
            nextCallback = {

            }
            pauseCallback = {
                mediaPlayerHelp.onAlbumClick(null)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        /**
         * 先更新音乐信息 然后刷新界面
         */
        initArgs()
        onContentInit()
    }

    /**
     * 界面相关初始化
     *  每次进入新界面都应该调用此方法刷新界面
     */
    private fun onContentInit() {
        Glide.with(this)
            .load(info?.poster)
            .placeholder(R.drawable.test)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 10)))
            .into(binding.ivBackground)

        Glide.with(this)
            .load(info?.poster)
            .placeholder(R.drawable.test)
            .into(binding.civAlbum)
        binding.tvMusicName.text = info?.name
        binding.tvMaker.text = info?.author
        binding.mrInner.setValueInner(this)
    }

    private var detector: GestureDetector? = null
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (detector == null)
            detector = GestureDetector(this, MyGestureDetector())
        detector?.onTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun initArgs() {
        super.initArgs()
        intent.extras?.apply {
            id = getString("id")
        }
        info =
            LocalHelper.instance.loadMusicById(id ?: "") ?: MediaPlayerHelp.instance.liveMusic.value
        LoggerUtil.d("$info $id")
    }

    private fun playMusic(music: Music?) {
        mediaPlayerHelp = MediaPlayerHelp.instance.apply {
            setMusic(music, this@PlayMusicActivity, binding)
        }
    }


    inner class MyGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            LoggerUtil.d(distanceY)
            if (distanceY < -15) {
                onBackPressed()
            }
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

    }

}