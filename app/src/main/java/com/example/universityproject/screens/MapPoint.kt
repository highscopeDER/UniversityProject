package com.example.universityproject.screens

data class MapPoint(
    val x: Float,
    val y: Float,
) {
    private val diff = 100f

    val xStart: Float = x - diff
    val xEnd: Float = x + diff
    val yStart: Float = y - diff
    val yEnd: Float = y + diff
}