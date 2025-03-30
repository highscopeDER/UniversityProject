package com.example.domain.repositories

import com.example.domain.models.alias.GraphData
import com.example.domain.models.Route
import com.example.domain.models.RoutePoint
import com.example.domain.models.alias.Points

interface PathFindRepository {

    fun setUpGraph(p: Map<String, String>, d: GraphData, c: Points)

    fun buildRoute(start: RoutePoint, end: RoutePoint) : Route


}