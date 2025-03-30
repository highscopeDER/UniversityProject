package com.example.universityproject.screens.map

import com.example.domain.models.RoutePoint

class PathEdgesSetter (
    val first: (item: RoutePoint) -> Unit,
    val second: (item: RoutePoint) -> Unit
)



