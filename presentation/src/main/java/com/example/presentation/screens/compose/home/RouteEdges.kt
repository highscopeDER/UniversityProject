package com.example.presentation.screens.compose.home

import com.example.domain.models.RoutePoint

data class RouteEdges(
    val start: RoutePoint?,
    val end: RoutePoint?
) {
    fun bothEdgesSelected(): Boolean = start != null && end != null
}

