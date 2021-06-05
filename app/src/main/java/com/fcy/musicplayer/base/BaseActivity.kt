package com.fcy.musicplayer.base


import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import com.fcy.musicplayer.R
import com.fcy.musicplayer.util.BarUtils

open class BaseActivity : AppCompatActivity() {

    var onMeClick: () -> Unit = {}
    var onBackClick: () -> Unit = {}

    override fun onContentChanged() {
        initViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs()
    }

    /**
     * 初始化intent附带的参数
     */
    open fun initArgs() {

    }

    open fun initViewModel() {

    }

    protected open fun initToolBar(
        canGoBack: Boolean = false,
        showMe: Boolean,
        title: String,
        @ColorRes color: Int = R.color.purple_200
    ) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = resources.getColor(color, theme)
        val navBar = findViewById<FrameLayout>(R.id.navBar) ?: return
        navBar.setBackgroundColor(resources.getColor(color, theme))
        navBar.findViewById<ImageView>(R.id.iv_me).apply {
            if (!showMe) visibility = View.INVISIBLE
            setOnClickListener {
                onMeClick.invoke()
            }
        }
        navBar.findViewById<ImageView>(R.id.iv_back).apply {
            if (!canGoBack) visibility = View.INVISIBLE
            setOnClickListener {
                onBackClick.invoke()
            }
        }
        navBar.findViewById<TextView>(R.id.tv_title).text = title
    }

}
