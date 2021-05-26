package com.fcy.musicplayer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        initRecommend()
        initHot()
    }

    /**
     * 初始化热门音乐列表
     */
    private fun initHot() {
        binding.rvHot.apply {
            val list = MutableList<String>(20) { it ->
                "1$it"
            }
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = object : MyRecyclerAdapter<String>(list) {
                override fun getLayoutId(viewType: Int): Int = R.layout.item_hot

                override fun bindView(holder: MyHolder?, position: Int, t: String?) {
                    holder?.itemView?.setOnClickListener {
                        startActivity(Intent(this@MainActivity, PlayMusicActivity::class.java))
                    }
                }
            }
        }

    }

    /**
     * 初始化推荐列表
     */
    private fun initRecommend() {
        binding.rvRecommend.apply {
            val list = MutableList<String>(6) { it ->
                "1$it"
            }
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            addItemDecoration(
                GridSpaceItemDecoration(2, binding.rvRecommend)
            )
            binding.rvRecommend.adapter = object : MyRecyclerAdapter<String>(list) {
                override fun getLayoutId(viewType: Int): Int = R.layout.item_recomend

                override fun bindView(holder: MyHolder?, position: Int, t: String?) {

                }

            }
        }

    }

}