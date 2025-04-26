package com.example.domain.repositories

import com.example.domain.models.alias.GraphData
import com.example.domain.models.Route
import com.example.domain.models.RoutePoint
import com.example.domain.models.alias.Points
import kotlinx.coroutines.flow.Flow

interface PathFindRepository {

    fun setUpGraph(p: Map<String, String>, d: GraphData, c: Points)

    suspend fun buildRoute(start: RoutePoint, end: RoutePoint) : Flow<Route>


}