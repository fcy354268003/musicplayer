package com.fcy.musicplayer.base


import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import com.fcy.musicplayer.R

open class BaseActivity : AppCompatActivity() {


    override fun onContentChanged() {
        initToolBar()
    }
    protected open fun initToolBar(@ColorRes color: Int = R.color.purple_200) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = resources.getColor(color, theme)
        findViewById<FrameLayout>(R.id.navBar).setBackgroundColor(resources.getColor(color, theme))
    }
}
