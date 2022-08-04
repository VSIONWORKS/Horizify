package com.horizon.horizify.extensions

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

internal val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)