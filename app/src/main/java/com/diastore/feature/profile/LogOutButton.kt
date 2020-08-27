package com.diastore.feature.profile

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.diastore.databinding.ViewLogOutButtonBinding

class LogOutButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes) {

    init {
        ViewLogOutButtonBinding.inflate(LayoutInflater.from(context), this)
    }
}