package com.fcy.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.fcy.musicplayer.widget.InputView

class RegisterActivity : AppCompatActivity() {
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

        }
    }
}