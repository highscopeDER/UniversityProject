package com.example.universityproject.model

import android.graphics.PointF
import android.graphics.drawable.Drawable

data class AreaInfoItem(
    val name: String,
    val points: List<PointF>,
    val icon: Int? = null
)
