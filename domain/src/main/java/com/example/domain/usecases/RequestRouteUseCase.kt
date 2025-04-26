package com.example.domain.usecases

import com.example.domain.models.Route
import com.example.domain.models.RoutePoint
import com.example.domain.repositories.PathFindRepository
import kotlinx.coroutines.flow.Flow

class RequestRouteUseCase(
    private val pathFindRepository: PathFindRepository
) {

    suspend fun execute(start: RoutePoint, end: RoutePoint): Flow<Route> {
//        pathFindRepository.buildRoute(start, end).routeParts.forEach { path ->
//            println(path.pathPoints.map { it.name })
//        }
        return pathFindRepository.buildRoute(start, end)

    }

}