package com.fcy.musicplayer

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.databinding.ActivityPlayMusicBinding
import com.fcy.musicplayer.db.entity.Music
import com.fcy.musicplayer.helps.MediaPlayerHelp
import com.fcy.musicplayer.repository.LocalHelper
import com.fcy.musicplayer.util.LoggerUtil
import jp.wasabeef.glide.transformations.BlurTransformation

class PlayMusicActivity : BaseActivity() {
    private lateinit var mediaPlayerHelp: MediaPlayerHelp
    private var isPlaying: Boolean = true
    private var id: String? = null
    private var info: Music? = null
    private lateinit var binding: ActivityPlayMusicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_play_music
        )
        playMusic(info)
        onContentInit()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
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
        binding.mrInner.setValue(this)
    }

    override fun initArgs() {
        super.initArgs()
        intent.extras?.apply {
            id = getString("id")
        }
        info = LocalHelper.instance.loadMusicById(id ?: "")
        LoggerUtil.d("$info $id")
    }


    private fun playMusic(music: Music?) {
        mediaPlayerHelp = MediaPlayerHelp.instance.apply {
            setMusic(music, this@PlayMusicActivity, binding)
            start()
        }
    }


}