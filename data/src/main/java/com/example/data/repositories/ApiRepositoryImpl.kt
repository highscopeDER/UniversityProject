package com.example.data.repositories

import com.example.data.datasources.ApiDataSource
import com.example.domain.models.alias.Floors
import com.example.domain.models.alias.GraphData
import com.example.domain.models.alias.Points
import com.example.domain.models.RoutePoint
import com.example.domain.repositories.ApiRepository
import kotlinx.coroutines.flow.Flow

class ApiRepositoryImpl : ApiRepository {

    private val api = ApiDataSource()

    override suspend fun requestClassroomsList(): Flow<Map<String, String>> = api.makeClassroomsRequest()

    override suspend fun requestGraphData(): Flow<GraphData> = api.makeGraphDataRequest()

    override suspend fun requestFloors(): Flow<Floors> = api.makeFloorsRequest()

    override suspend fun requestRoutePoints(): Flow<List<RoutePoint>> = api.makeRoutePointsRequest()

    override suspend fun requestPoints(): Flow<Points> = api.makeCoordinatesRequest()

}


