package com.example.domain.models

data class Floor (
    val enum: FloorsEnum,
    val areasInfo: List<AreaInfoItem>,
)