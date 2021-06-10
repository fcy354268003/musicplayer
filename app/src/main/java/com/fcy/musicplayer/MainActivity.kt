package com.fcy.musicplayer

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Pair
import android.view.Gravity
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.databinding.ActivityMainBinding
import com.fcy.musicplayer.databinding.ItemHotBinding
import com.fcy.musicplayer.databinding.ItemRecomendBinding
import com.fcy.musicplayer.db.entity.Album
import com.fcy.musicplayer.db.entity.Music
import com.fcy.musicplayer.helps.MediaPlayerHelp
import com.fcy.musicplayer.repository.LocalHelper
import com.fcy.musicplayer.util.LiveDataManager
import com.fcy.musicplayer.util.LoggerUtil
import com.fcy.musicplayer.util.MyRecyclerAdapter
import com.fcy.musicplayer.widget.GridSpaceItemDecoration
import com.fcy.musicplayer.widget.MusicRegulator

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private var requestManager: RequestManager? = null
    private var displayMetrics: DisplayMetrics? = DisplayMetrics()

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

                override fun bindView(holder: MyHolder, position: Int, t: Music) {
                    (holder.binding as? ItemHotBinding)?.apply {
                        lifecycleOwner = this@MainActivity
                        music = t
                        help = MediaPlayerHelp.instance
                        LoggerUtil.d("${MediaPlayerHelp.instance.liveMusic.value?.musicId}=${t.musicId}")
                        requestManager?.load(t.poster)?.into(squareImagineView)
                        csl.setOnClickListener {
                            val transBundle =
                                ActivityOptions.makeSceneTransitionAnimation(
                                    this@MainActivity,
                                    Pair.create(squareImagineView, "poster"),
                                    Pair.create(tvMakerName, "maker"),
                                    Pair.create(tvMusicName, "musicName")
                                ).toBundle()
                            val intent = Intent(this@MainActivity, PlayMusicActivity::class.java)
                            intent.putExtra("id", t.musicId)
                            startActivity(intent, transBundle)
                        }
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

    override fun onStart() {
        super.onStart()
        //判断当前是否有歌曲播放 是否显示底部控制框
        if (!TextUtils.isEmpty(MediaPlayerHelp.instance.liveMusic.value?.musicId))
            initBottomWindow()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            windowManager.defaultDisplay.getMetrics(displayMetrics)
        }
    }

    /**
     * 设置底部音乐控制栏
     */
    private fun initBottomWindow() {
        val manager: WindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val la = WindowManager.LayoutParams().apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            gravity = Gravity.BOTTOM
        }
        val musicRegulator = MusicRegulator(this, null).apply {
            background = ColorDrawable(Color.GRAY)
            pauseCallback = {
                MediaPlayerHelp.instance.onAlbumClick(null)
            }
            preCallback = {
                val idd = MediaPlayerHelp.instance.preMusic()
                idd?.let {
                    val loadMusicById = LocalHelper.instance.loadMusicById(it)
                    MediaPlayerHelp.instance.setMusic(loadMusicById, this@MainActivity, null)

                }
            }
            nextCallback = {
                val nextMusic = MediaPlayerHelp.instance.nextMusic()
                val loadMusicById = LocalHelper.instance.loadMusicById(nextMusic)
                MediaPlayerHelp.instance.setMusic(loadMusicById, this@MainActivity, null)
            }
            setValueOuter(this@MainActivity)
            onScrollUp = { binding.civ.callOnClick() }
            binding.civ.setOnClickListener { view ->
                val intent = Intent(this@MainActivity, PlayMusicActivity::class.java)
                this@MainActivity.startActivity(
                    intent,
                    ActivityOptions.makeSceneTransitionAnimation(
                        this@MainActivity,
                        Pair.create(view, "poster"),
                        Pair.create(binding.llInner, "ll_inner")
                    ).toBundle()
                )
            }
        }
        manager.addView(musicRegulator, la)
    }


}

