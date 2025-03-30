package com.example.universityproject.model

import android.graphics.RectF
import com.example.domain.models.FloorsEnum
import com.example.universityproject.R

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

val FloorsEnum.resource: Int
    get() = when(this) {
        FloorsEnum.H1 -> R.drawable.floorwtf2
        FloorsEnum.H2 -> R.drawable.floor2
        FloorsEnum.H3 -> R.drawable.floor3
        FloorsEnum.H4 -> R.drawable.floor4
        FloorsEnum.H5 -> R.drawable.floor5
    }





