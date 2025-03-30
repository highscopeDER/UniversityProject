package com.example.domain.usecases

import com.example.domain.models.RoutePoint
import com.example.domain.models.alias.Floors
import com.example.domain.models.alias.GraphData
import com.example.domain.models.alias.Points
import com.example.domain.repositories.ApiRepository
import com.example.domain.repositories.PathFindRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine

class LoadUseCase (
    private val apiRepo: ApiRepository,
    private val pathFindRepo: PathFindRepository
) {

    suspend fun execute() : Flow<Data> {
        combine(
            apiRepo.requestClassroomsList(),
            apiRepo.requestGraphData(),
            apiRepo.requestPoints()
        ) { p, d, c ->
            Wtf(p, d, c)
        }.collectLatest {
            pathFindRepo.setUpGraph(it.p, it.d, it.c)
        }

        return combine(
            apiRepo.requestFloors(),
            apiRepo.requestRoutePoints()
        ) { floors, classrooms ->
            Data(floors, classrooms)
        }

    }

    data class Wtf(
        val p: Map<String, String>,
        val d: GraphData,
        val c: Points
    )

    data class Data (
        val floors: Floors,
        val classrooms: List<RoutePoint>
    )

}