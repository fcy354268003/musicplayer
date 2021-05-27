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
import com.fcy.musicplayer.helps.UserHelp
import com.fcy.musicplayer.util.LoggerUtil
import com.fcy.musicplayer.viewmodel.LogInViewModel

class LogInActivity : BaseActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var mViewModel: LogInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoggerUtil.d("on create")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        binding.lifecycleOwner = this
        binding.context = this
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        LoggerUtil.d("onNewIntent")
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

        UserHelp.instance.login(this,mViewModel)
    }




}