package com.example.universityproject.model

import android.graphics.PointF
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

fun PointF.getSpecifiedCoordinates(mapWidth: Float, mapHeight: Float): PointF {
    val length = 112.5f
    val height = 29.9f

    val x_percent = x / length
    val y_percent = (height - y) / height

    return PointF(
        mapWidth * x_percent,
        mapHeight * y_percent
    )
}


fun List<PointF>.makeClickablePath(w: Float, h: Float) : ClickablePath {
    var first = true

    return ClickablePath().apply {

        val list = map {
            it.getSpecifiedCoordinates(w ,h)
        }

        list.forEach {
            if (first) {
                moveTo(it.x, it.y)
            }
            lineTo(it.x, it.y)
            first = false
        }

        close()

    }
}



