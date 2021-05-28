package com.fcy.musicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.helps.UserHelp
import com.fcy.musicplayer.widget.InputView

class RegisterActivity : BaseActivity() {
    private lateinit var phone: InputView
    private lateinit var pwd1: InputView
    private lateinit var pwd2: InputView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        phone = findViewById(R.id.input_phone)
        pwd1 = findViewById(R.id.input_passwd)
        pwd2 = findViewById(R.id.input_pwdSure)
        findViewById<Button>(R.id.btn_register).setOnClickListener {
            if (UserHelp.instance.register(phone.getInput(), pwd1.getInput(), pwd2.getInput())) {
                Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show()
                toLogin()
            }else Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show()
        }
    }

    private fun toLogin() {
        Intent(this, LogInActivity::class.java).apply {
            startActivity(this)
        }
    }
}