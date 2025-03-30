package com.example.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class FloorAreas(
    val num: Int,
    val rooms: List<Room>
)
