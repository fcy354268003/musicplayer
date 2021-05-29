package com.fcy.musicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.fcy.musicplayer.helps.UserHelp

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        Handler().postDelayed({
            val intent: Intent = if (UserHelp.instance.validateLogin(this)) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LogInActivity::class.java)
            }
            finish()
            startActivity(intent)
        }, 1000)
    }
}