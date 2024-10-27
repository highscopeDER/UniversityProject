package com.example.universityproject.dijkstra

import kotlin.reflect.typeOf

data class Graph<String>(
    val vertices: Set<String>,
    val edges: Map<String, List<String>>,
    val weights: Map<Pair<String, String>, Float>
)