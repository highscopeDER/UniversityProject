package com.example.universityproject.route

import android.graphics.Bitmap
import android.graphics.RectF

data class RouteStep(
    val bitmap: Bitmap,
    val floorInfo: String,
    val description: String,
    val focus: RectF
)
