package com.example.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val name: String,
    val label: String,
    val points: List<Pair<Float, Float>>
)
