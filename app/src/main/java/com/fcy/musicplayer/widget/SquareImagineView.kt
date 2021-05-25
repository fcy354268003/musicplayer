package com.fcy.musicplayer.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class SquareImagineView(context: Context, attributeSet: AttributeSet?) :
    AppCompatImageView(context, attributeSet) {
    // 让图片的高度等于宽度
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}