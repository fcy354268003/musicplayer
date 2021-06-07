package com.fcy.musicplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.fcy.musicplayer.R
import com.fcy.musicplayer.databinding.MusicRegulatorBinding
import com.fcy.musicplayer.databinding.MusicRegulatorOuterBinding
import com.fcy.musicplayer.helps.MediaPlayerHelp

/**
 * 应该在进入播放音乐界面的时候 创建实例 然后托管给 MediaPlayerHelp
 * MediaPlayerHelp控制其进度条的现实情况
 *      要注意的是 MediaPlayerHelper应该检测Activity的生命周期变化及时去除这个viewGroup的索引
 */
open class MusicRegulator(context: Context, attributeSet: AttributeSet?) :
    FrameLayout(context, attributeSet) {
    var preCallback: () -> Unit = {}
    var nextCallback: () -> Unit = {}
    var pauseCallback: () -> Unit = {}

    val binding: MusicRegulatorOuterBinding =
        DataBindingUtil.inflate<MusicRegulatorOuterBinding>(
            LayoutInflater.from(context),
            R.layout.music_regulator_outer,
            this,
            false
        ).apply {
            ivPrevious.setOnClickListener {
                preCallback.invoke()
            }
            ivNext.setOnClickListener {
                nextCallback.invoke()
            }
            ivPause.setOnClickListener {
                pauseCallback.invoke()
            }
        }

    init {
        addView(binding.root)
    }

    fun setValueInner(life: LifecycleOwner) {
        binding.lifecycleOwner = life
        binding.value = MediaPlayerHelp.instance
    }

    fun setValueOuter(life: LifecycleOwner) {
        binding.lifecycleOwner = life
        binding.value = MediaPlayerHelp.instance
        binding.flOuter.visibility = View.VISIBLE
        MediaPlayerHelp.instance.liveMusic.observe(life) {
            Glide.with(this)
                .load(it.poster)
                .into(binding.civ)
            binding.tvMaker.text = it.author
            binding.tvMusicName.text = it.name
        }
    }

}