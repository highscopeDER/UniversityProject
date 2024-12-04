package com.example.universityproject.model

import android.graphics.PointF
import android.graphics.RectF
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.min

fun RectF.getSpecifiedCoordinates(mapWidth: Float, mapHeight: Float): RectF {

    val result: RectF = RectF()

    val length = 112.753f
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

fun RectF.wtf(w: Float, h: Float, setZoom: (s: Float, fx: Float, fy: Float) -> Unit) {

    val sX = w / this.width()
    val sY = h / this.height()

    val fX = this.centerX() / w
    val fY = this.centerY() / h

    setZoom(min(sX, sY) / 10, fX, fY)

}

fun PointF.getSpecifiedCoordinates(mapWidth: Float, mapHeight: Float): PointF {
    val length = 112.753f
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

fun String.isLadder(): Boolean = this.last() == 'l'

val String.floor: Int
    get() = this[1].digitToInt()

val String.building: Int
    get() = when(this[0]) {
            'a' -> 1
            'b' -> 2
            'c' -> 3
            'd' -> 4
            'e' -> 5
            'f' -> 6
            'g' -> 7
            'h' -> 8
            'i' -> 9
            'j' -> 10
            'k' -> 11
            'l' -> 12
            'm' -> 13
            else -> 0
        }


