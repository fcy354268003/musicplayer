package com.fcy.musicplayer.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import com.fcy.musicplayer.R

class MusicPlayerAlbum(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var mRadius: Int
    private var mWidth: Int
    private var color: Int
    private var picSrc: Int
    private var bitmapPaint: Paint = Paint().apply {
        isAntiAlias = true
    }
    private var boundaryPaint: Paint = Paint()
    private var imagineView: ImageView

    init {
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attrs, R.styleable.MusicPlayerAlbum)
        mRadius = obtainStyledAttributes.getInteger(R.styleable.MusicPlayerAlbum_radius, 30)
        mWidth = obtainStyledAttributes.getInteger(R.styleable.MusicPlayerAlbum_boundary_width, 10)
        color = obtainStyledAttributes.getColor(
            R.styleable.MusicPlayerAlbum_boundary_color,
            Color.BLACK
        )
        picSrc = obtainStyledAttributes.getResourceId(
            R.styleable.MusicPlayerAlbum_pic_src,
            R.drawable.test
        )
        // 初始化图片画笔
        val bitmap = BitmapFactory.decodeResource(resources, picSrc)
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        bitmapPaint.shader = bitmapShader
        val url = obtainStyledAttributes.getString(R.styleable.MusicPlayerAlbum_src_url)
        imagineView = ImageView(getContext())
        obtainStyledAttributes.recycle()
    }





}