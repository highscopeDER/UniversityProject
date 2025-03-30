package com.example.domain.repositories

import com.example.domain.models.alias.Floors
import com.example.domain.models.alias.GraphData
import com.example.domain.models.alias.Points
import com.example.domain.models.RoutePoint
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

    suspend fun requestClassroomsList(): Flow<Map<String, String>>

    suspend fun requestGraphData(): Flow<GraphData>

    suspend fun requestFloors(): Flow<Floors>

    suspend fun requestRoutePoints(): Flow<List<RoutePoint>>

    suspend fun requestPoints(): Flow<Points>


}