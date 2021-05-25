package com.fcy.musicplayer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.databinding.ActivityMainBinding
import com.fcy.musicplayer.util.LoggerUtil
import com.fcy.musicplayer.util.MyRecyclerAdapter
import com.fcy.musicplayer.widget.GridSpaceItemDecoration

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        onContentInit()
    }

    private fun onContentInit() {
        initToolBar(canGoBack = false, showMe = true, title = "慕课音乐")
        binding.navBar.findViewById<ImageView>(R.id.iv_me).setOnClickListener {
            val intent = Intent(this, MeActivity::class.java)
            startActivity(intent)
        }
        initRecommend()
    }

    /**
     * 初始化推荐列表
     */
    private fun initRecommend() {
        val list = MutableList<String>(6) { it ->
            "1$it"
        }
        binding.rvRecommend.layoutManager = GridLayoutManager(this, 3)
        binding.rvRecommend.addItemDecoration(
            GridSpaceItemDecoration(4 ,binding.rvRecommend)
        )
        binding.rvRecommend.adapter = object : MyRecyclerAdapter<String>(list) {
            override fun getLayoutId(viewType: Int): Int = R.layout.item_recomend

            override fun bindView(holder: MyHolder?, position: Int, t: String?) {

            }

        }
    }

}