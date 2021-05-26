package com.fcy.musicplayer

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fcy.musicplayer.base.BaseActivity
import com.fcy.musicplayer.util.MyRecyclerAdapter

class AlbumActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
    }

    override fun onContentChanged() {
        super.onContentChanged()
        initToolBar(canGoBack = false, showMe = false, title = "专辑列表")
        findViewById<RecyclerView>(R.id.rv_album).apply {
            val list = MutableList<String>(20) { it ->
                "1$it"
            }
            layoutManager = LinearLayoutManager(this@AlbumActivity)
            adapter = object : MyRecyclerAdapter<String>(list) {
                override fun getLayoutId(viewType: Int): Int = R.layout.item_hot
                override fun bindView(holder: MyHolder?, position: Int, t: String?) {
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