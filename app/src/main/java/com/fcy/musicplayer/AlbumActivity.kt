package com.fcy.musicplayer

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.databinding.ItemHotBinding
import com.fcy.musicplayer.db.entity.Music
import com.fcy.musicplayer.helps.MediaPlayerHelp
import com.fcy.musicplayer.repository.LocalHelper
import com.fcy.musicplayer.util.LiveDataManager
import com.fcy.musicplayer.util.LoggerUtil
import com.fcy.musicplayer.util.MyRecyclerAdapter

class AlbumActivity : BaseActivity() {
    private var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
    }

    private var requestManager: RequestManager? = null
    override fun initArgs() {
        super.initArgs()
        id = intent?.extras?.getString("id")
        LoggerUtil.d("===$id===")
    }

    override fun onContentChanged() {
        super.onContentChanged()
        initToolBar(canGoBack = false, showMe = false, title = "专辑列表")
        requestManager = Glide.with(this)
        initList(LocalHelper.instance.loadAlbumById(id ?: "")?.list ?: listOf<Music>())
    }

    private fun initList(list: List<Music>) {
        findViewById<RecyclerView>(R.id.rv_album).apply {
            layoutManager = LinearLayoutManager(this@AlbumActivity)
            adapter = object : MyRecyclerAdapter<Music>(list) {
                override fun getLayoutId(viewType: Int): Int = R.layout.item_hot

                override fun bindView(holder: MyHolder, position: Int, t: Music?) {
                    (holder.binding as? ItemHotBinding)?.apply {
                        LoggerUtil.d("bindView")
                        lifecycleOwner = this@AlbumActivity
                        tvMakerName.text = t?.author
                        tvMusicName.text = t?.name
                        music = t
                        help = MediaPlayerHelp.instance
                        requestManager?.load(t?.poster)?.into(squareImagineView)
                        csl.setOnClickListener {
                            val transBundle =
                                ActivityOptions.makeSceneTransitionAnimation(
                                    this@AlbumActivity,
                                    Pair.create(squareImagineView, "poster"),
                                    Pair.create(tvMakerName, "maker"),
                                    Pair.create(tvMusicName, "musicName")
                                ).toBundle()
                            val intent = Intent(this@AlbumActivity, PlayMusicActivity::class.java)
                            intent.putExtra("id", t?.musicId)
                            startActivity(intent, transBundle)
                        }
                    }

                }
            }
            addItemDecoration(
                DividerItemDecoration(
                    this@AlbumActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

}