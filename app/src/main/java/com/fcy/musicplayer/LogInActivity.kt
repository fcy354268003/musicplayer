package com.fcy.musicplayer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.databinding.ActivityLogInBinding
import com.fcy.musicplayer.util.LoggerUtil
import com.fcy.musicplayer.viewmodel.LogInViewModel

class LogInActivity : BaseActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var mViewModel: LogInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        binding.lifecycleOwner = this
        binding.context = this
    }

    override fun onContentChanged() {
        super.onContentChanged()
        initToolBar(canGoBack = false, showMe = false, title = "登录")
    }

    override fun initViewModel() {
        mViewModel = ViewModelProvider.NewInstanceFactory().create(LogInViewModel::class.java)
    }

    fun onLoginClick() {
        mViewModel.phone.value = binding.inputPhone.getInput()
        mViewModel.password.value = binding.inputPasswd.getInput()
        LoggerUtil.d(binding.inputPhone.getInput())
        val result = mViewModel.checkPhone()
        Toast.makeText(this, result.first, Toast.LENGTH_SHORT).show()
        LoggerUtil.d(result.second)
        if (result.second == 2)
            toMain()
    }

    private fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}