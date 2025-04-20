package com.example.domain.usecases

import com.example.domain.models.Route
import com.example.domain.models.RoutePoint
import com.example.domain.repositories.PathFindRepository

class RequestRouteUseCase(
    private val pathFindRepository: PathFindRepository
) {

    fun execute(start: RoutePoint, end: RoutePoint): Route {
        pathFindRepository.buildRoute(start, end).routeParts.forEach { path ->
            println(path.pathPoints.map { it.name })
        }
        return pathFindRepository.buildRoute(start, end)

    }

}