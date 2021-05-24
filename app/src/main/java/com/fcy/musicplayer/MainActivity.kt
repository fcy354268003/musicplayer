package com.fcy.musicplayer

import android.os.Bundle
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.util.LoggerUtil

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        actionBar?.hide()
        if(actionBar == null)
            LoggerUtil.d("asdsad")
    }

}