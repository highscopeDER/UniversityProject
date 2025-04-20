package com.example.data.repositories

import com.example.data.dijkstra.Dijkstra
import com.example.data.models.PointType
import com.example.data.models.building
import com.example.data.models.floor
import com.example.data.models.isLadder
import com.example.data.models.isTransition
import com.example.data.models.toFloorsEnum
import com.example.data.models.type
import com.example.domain.models.alias.GraphData
import com.example.domain.models.PointPosition
import com.example.domain.models.Route
import com.example.domain.models.RoutePart
import com.example.domain.models.RoutePoint
import com.example.domain.models.alias.Points
import com.example.domain.repositories.PathFindRepository

class PathFindRepositoryImpl : PathFindRepository {

    private val algorithm = Dijkstra()

    override fun setUpGraph(p: Map<String, String>, d: GraphData, c: Points) {
        algorithm.buildGraph(p, d, c)
    }

    override fun buildRoute(start: RoutePoint, end: RoutePoint): Route {
        return Route(
            splitPath(
                algorithm.buildRoute(start.name, end.name),
                start.label,
                end.label
            )
        )
    }

    private fun resolveFloorInfo(p: PointPosition): String {
        return "${p.building} корпус, ${p.floor} этаж"
    }

    private fun splitPath(fullPath: List<PointPosition>, startClassroom: String, endClassroom: String): List<RoutePart> =
         fullPath
            .filter { it.isLadder() || it.isTransition() }
            .chunked(2)
            .flatMap {
                listOf( fullPath.indexOf(it.last()), fullPath.indexOf(it.last()))
            }
            .toMutableList()
            .apply {
                add(0, 0)
                add(fullPath.lastIndex + 1)
            }
            .chunked(2)
            .map {
                val part = fullPath.subList(it.first(), it.last())
                RoutePart(
                    source = part.first().toFloorsEnum(),
                    sourceInfo = resolveFloorInfo(part.first()),
                    pathPoints = part,
                    description = buildString {

                        when {
                            part.first().type == PointType.NO_TYPE  && part.last().type == PointType.NO_TYPE-> {
                                append("от аудитории $startClassroom пройдите в аудиторию $endClassroom")
                            }

                            part.first().type == PointType.NO_TYPE  && part.last().type == PointType.STAIR-> {
                                append("от аудитории $startClassroom пройдите к лестнице и ")
                                val nextPart = fullPath[fullPath.indexOf(part.last()) + 1]
                                append(if (part.last().floor > nextPart.floor) "спуститесь " else "поднимитесь ")
                                append("на ${nextPart.floor} этаж")
                            }

                            part.first().type == PointType.NO_TYPE  && part.last().type == PointType.TRANSITION-> {
                                append("от аудитории $startClassroom пройдите к переходу в ")
                                val nextPart = fullPath[fullPath.indexOf(part.last()) + 1]
                                append("${nextPart.building} корпус")
                            }

                            part.first().type == PointType.STAIR  && part.last().type == PointType.NO_TYPE-> {
                                append("от лестницы пройдите в аудиторию $endClassroom")
                            }

                            part.first().type == PointType.TRANSITION  && part.last().type == PointType.NO_TYPE-> {
                                append("от перехода пройдите в аудиторию $endClassroom")
                            }

                            part.first().type == PointType.STAIR  && part.last().type == PointType.TRANSITION-> {
                                append("от лестницы пройдите к переходу в ")
                                val nextPart = fullPath[fullPath.indexOf(part.last()) + 1]
                                append("${nextPart.building} корпус")
                            }

                            part.first().type == PointType.TRANSITION  && part.last().type == PointType.STAIR-> {
                                append("от перехода пройдите к лестнице и ")
                                val nextPart = fullPath[fullPath.indexOf(part.last()) + 1]
                                append(if (part.last().floor > nextPart.floor) "спуститесь " else "поднимитесь ")
                                append("на ${nextPart.floor} этаж")
                            }

                            part.first().type == PointType.STAIR  && part.last().type == PointType.STAIR-> {
                                append("от лестницы пройдите к другой лестнице и ")
                                val nextPart = fullPath[fullPath.indexOf(part.last()) + 1]
                                append(if (part.last().floor > nextPart.floor) "спуститесь " else "поднимитесь ")
                                append("на ${nextPart.floor} этаж")
                            }

                        }

                    }
                )
            }

}