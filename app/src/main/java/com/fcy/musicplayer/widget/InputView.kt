package com.fcy.musicplayer.widget

import android.content.Context
import android.content.res.TypedArray
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import com.fcy.musicplayer.R
import com.fcy.musicplayer.util.LoggerUtil

/**
 *     1. input_icon:图标
 *     2. input_hint：输入框提示
 *     3. is_pass :是否要密文展示
 */
class InputView(context: Context, attributeSet: AttributeSet?) :
    FrameLayout(context, attributeSet) {
    private var icon: Int = 0
    private var hint: String = ""
    private var isPassword: Boolean = false
    private lateinit var mView: View
    private lateinit var mInput: EditText
    private lateinit var mImageView: ImageView

    constructor(context: Context) : this(context, null)

    init {
        if (attributeSet != null) {
            val obtainStyledAttributes: TypedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.InputView)
            icon = obtainStyledAttributes.getResourceId(
                R.styleable.InputView_input_icon,
                R.drawable.logo
            )
            hint = obtainStyledAttributes.getString(R.styleable.InputView_input_hint) ?: ""
            isPassword =
                obtainStyledAttributes.getBoolean(R.styleable.InputView_input_isPassword, false)
            obtainStyledAttributes.recycle()

            mView = LayoutInflater.from(context).inflate(R.layout.input_view, this, false)
            mInput = mView.findViewById(R.id.et_input)
            mImageView = mView.findViewById(R.id.iv_icon)
            mInput.hint = hint
            mImageView.setImageResource(icon)
            if (isPassword) {
                mInput.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                mInput.inputType = InputType.TYPE_CLASS_PHONE
            }
            addView(mView)
        }
    }

    /**
     * 返回输入内容
     */
    fun getInput(): String {
        return mInput.text.toString().trim()
    }

}