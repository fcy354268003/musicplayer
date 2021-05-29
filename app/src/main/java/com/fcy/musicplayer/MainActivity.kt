package com.fcy.musicplayer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.databinding.ActivityMainBinding
import com.fcy.musicplayer.databinding.ItemHotBinding
import com.fcy.musicplayer.databinding.ItemRecomendBinding
import com.fcy.musicplayer.db.entity.Album
import com.fcy.musicplayer.db.entity.Music
import com.fcy.musicplayer.repository.LocalHelper
import com.fcy.musicplayer.util.LiveDataManager
import com.fcy.musicplayer.util.LoggerUtil
import com.fcy.musicplayer.util.MyRecyclerAdapter
import com.fcy.musicplayer.widget.GridSpaceItemDecoration

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private var requestManager: RequestManager? = null
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        LocalHelper.instance.apply {
            loadMusicAsync()
            loadAlbumAsync()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        onContentInit()

    }

    /**
     * 注册监听者监听回调
     */
    fun setObserver() {
        //监听album回调
        LiveDataManager.with<List<Album>>("album").observe(this, {
            if (it is List<*>) {
                initRecommend(it as List<Album>)
            }
        })

        //监听music回调
        LiveDataManager.with<List<Music>>("music").observe(this, {
            if (it is List<*>) {
                LoggerUtil.d(it.size)
                initHot(it as List<Music>)
            }
        })


    }

    private fun onContentInit() {
        requestManager = Glide.with(this)
        initToolBar(canGoBack = false, showMe = true, title = "慕课音乐")
        initRecommend(listOf())
        initHot(listOf())
        onMeClick = {
            Intent(this, MeActivity::class.java).apply {
                startActivity(this)
            }
        }
        setObserver()
    }

    /**
     * 初始化热门音乐列表
     */
    private fun initHot(list: List<Music>) {
        binding.rvHot.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = object : MyRecyclerAdapter<Music>(list) {
                override fun getLayoutId(viewType: Int): Int = R.layout.item_hot

                override fun bindView(holder: MyHolder, position: Int, t: Music?) {
                    (holder.binding as? ItemHotBinding)?.apply {
                        tvMakerName.text = t?.author
                        tvMusicName.text = t?.name
                        requestManager?.load(t?.poster)?.into(squareImagineView)
                    }
                    holder.itemView.setOnClickListener {
                        val intent = Intent(this@MainActivity, PlayMusicActivity::class.java)
                        intent.putExtra("id", t?.musicId)
                        startActivity(intent)
                    }
                }
            }
        }

    }

    /**
     * 初始化推荐列表
     */
    private fun initRecommend(list: List<Album>) {
        binding.rvRecommend.apply {

            layoutManager = GridLayoutManager(this@MainActivity, 3)
            addItemDecoration(
                GridSpaceItemDecoration(2, binding.rvRecommend)
            )
            binding.rvRecommend.adapter = object : MyRecyclerAdapter<Album>(list) {
                override fun getLayoutId(viewType: Int): Int = R.layout.item_recomend

                override fun bindView(holder: MyHolder, position: Int, t: Album?) {
                    (holder.binding as? ItemRecomendBinding)?.apply {
                        tvAlbumName.text = t?.name
                        tvNumPlay.text = t?.playNum
                        requestManager?.load(t?.poster)?.into(sivPoster)
                    }
                    holder.itemView.setOnClickListener {
                        val intent = Intent(this@MainActivity, AlbumActivity::class.java)
                        intent.putExtra("id", t?.albumId)
                        startActivity(intent)
                    }
                }

            }
        }

    }

}

