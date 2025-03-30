package com.example.domain.models

data class AreaInfoItem(
    val point: RoutePoint,
    val points: List<Pair<Float, Float>>,
    val icon: Int? = null
)
