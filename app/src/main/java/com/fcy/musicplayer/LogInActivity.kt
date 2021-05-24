package com.fcy.musicplayer

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import com.fcy.musicplayer.base.BaseActivity

class LogInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
    }

    override fun initToolBar(@ColorRes color: Int) {
        super.initToolBar(color)
        findViewById<TextView>(R.id.tv_title).text = "登录"
        findViewById<ImageView>(R.id.iv_back).visibility = View.INVISIBLE
        findViewById<ImageView>(R.id.iv_me).visibility = View.INVISIBLE
    }
}