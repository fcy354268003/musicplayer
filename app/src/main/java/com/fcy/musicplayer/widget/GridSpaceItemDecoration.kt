package com.fcy.musicplayer.widget

import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

class GridSpaceItemDecoration(private val mWidth: Int,recyclerView: RecyclerView) :
    RecyclerView.ItemDecoration() {
    init {
        val layoutParams = recyclerView.layoutParams
        layoutParams as LinearLayout.LayoutParams
        layoutParams.leftMargin = -mWidth
        recyclerView.layoutParams = layoutParams
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = mWidth

    }

}