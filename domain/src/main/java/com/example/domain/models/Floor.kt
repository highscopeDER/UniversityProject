package com.example.domain.models

data class Floor (
    val enum: FloorsEnum,
    val areasInfo: List<AreaInfoItem>,
    val bounds: Pair<Float, Float> = Pair(112.753f, 29.9f)
)