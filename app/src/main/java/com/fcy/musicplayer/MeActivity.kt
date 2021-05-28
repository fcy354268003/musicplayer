package com.fcy.musicplayer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.helps.UserHelp

class MeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_me)
        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            if (UserHelp.instance.logout(this))
                toLogIn()
        }
        initToolBar(canGoBack = true, showMe = false, "我的")
        onBackClick = {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun toLogIn() {
        Intent(this, LogInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    }

}