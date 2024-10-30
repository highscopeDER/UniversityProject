package com.example.universityproject.dijkstra

data class Graph<String>(
    val vertices: Set<String>,
    val edges: Map<String, List<String>>,
    val weights: Map<Pair<String, String>, Float>
)