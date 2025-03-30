package com.example.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class RoutePart(
    val source: FloorsEnum,
    val sourceInfo: String,
    val pathPoints: List<PointPosition>,
    val description: String
)
