package com.example.universityproject.screens.map.clickable

import android.graphics.PointF
import com.example.universityproject.model.RoutePoint

data class AreaInfoItem(
    val point: RoutePoint,
    val points: List<PointF>,
    val icon: Int? = null
)
