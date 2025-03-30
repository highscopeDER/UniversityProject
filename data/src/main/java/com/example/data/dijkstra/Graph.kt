package com.example.data.dijkstra

import com.example.domain.models.alias.GraphData
import com.example.domain.models.alias.Points
import com.example.domain.models.RoutePoint
import kotlin.math.pow
import kotlin.math.sqrt

data class Graph<String>(
    val vertices: Set<String>,
    val edges: Map<String, List<String>>,
    val weights: Map<Pair<String, String>, Float>
)