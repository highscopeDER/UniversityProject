package com.example.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Route (
    val routeParts: List<RoutePart>
) {

    fun encodeToString(): String = Json.encodeToString(value = this)

    companion object {
        fun decodeFromString(s: String) = Json.decodeFromString<Route>(s)
    }
}
