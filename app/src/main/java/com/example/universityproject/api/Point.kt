package com.example.universityproject.api

data class Point(
    val pointId: Int,
    val pointName: String,
    val type: PointTypesEnum,
    val description: String,
    val pointX: Float,
    val pointY: Float
)
