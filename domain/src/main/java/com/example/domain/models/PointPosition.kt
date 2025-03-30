package com.example.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PointPosition(
    val name: String,
    val x: Float,
    val y: Float
) {
//    fun getSpecifiedCoordinates(mapWidth: Float, mapHeight: Float): PointF {
//        val length = 112.753f
//        val height = 29.9f
//
//        val x_percent = x / length
//        val y_percent = (height - y) / height
//
//        return PointF(
//            mapWidth * x_percent,
//            mapHeight * y_percent
//        )
//    }
}


