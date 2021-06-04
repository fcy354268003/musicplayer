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
import com.fcy.musicplayer.R
import com.fcy.musicplayer.databinding.MusicRegulatorBinding
import com.fcy.musicplayer.helps.MediaPlayerHelp

/**
 * 应该在进入播放音乐界面的时候 创建实例 然后托管给 MediaPlayerHelp
 * MediaPlayerHelp控制其进度条的现实情况
 *      要注意的是 MediaPlayerHelper应该检测Activity的生命周期变化及时去除这个viewGroup的索引
 */
class MusicRegulator(context: Context, attributeSet: AttributeSet?) :
    FrameLayout(context, attributeSet) {
    var preCallback: () -> Unit = {

    }
    var nextCallback: () -> Unit = {}
    var pauseCallback: () -> Unit = {}
    private val binding: MusicRegulatorBinding = DataBindingUtil.inflate<MusicRegulatorBinding>(
        LayoutInflater.from(context),
        R.layout.music_regulator,
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

    fun setValue(life: LifecycleOwner) {
        binding.lifecycleOwner = life
        binding.value = MediaPlayerHelp.instance
    }
}