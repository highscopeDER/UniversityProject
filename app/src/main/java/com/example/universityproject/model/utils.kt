package com.example.universityproject.model

import android.graphics.RectF

fun RectF.getSpecifiedCoordinates(mapWidth: Float, mapHeight: Float): RectF {

    val result: RectF = RectF()

    val length = 112.5f
    val height = 29.9f

    var x_percent = left / length
    var y_percent = (height - top) / height

    result.left = mapWidth * x_percent
    result.top = mapHeight * y_percent

    x_percent = right / length
    y_percent = (height - bottom) / height

    result.right = mapWidth * x_percent
    result.bottom = mapHeight * y_percent

    return result
}

