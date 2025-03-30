package com.example.data.repositories

import com.example.data.dijkstra.Dijkstra
import com.example.data.models.building
import com.example.data.models.floor
import com.example.data.models.isLadder
import com.example.data.models.toFloorsEnum
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
            splitByFloors(
                algorithm.buildRoute(start.name, end.name),
                start.label,
                end.label
            )
        )
    }

    private fun resolveFloorInfo(pointName: String): String {
        return "${pointName.building} корпус, ${pointName.floor} этаж"
    }

    private fun splitByFloors(fullPath: List<PointPosition>, startClassroom: String, endClassroom: String): List<RoutePart> {

        var ladderEntry: Int = 0
        var count: Boolean = false
        val splitters: MutableList<Pair<Int, Int>> = mutableListOf()

        fullPath.forEach { item ->
            if (item.name.isLadder()) {
                if (count) {
                    val index = fullPath.indexOf(item)
                    splitters.add(Pair(ladderEntry, index))
                    ladderEntry = index
                }
                count = !count
            }
        }

        splitters.add(Pair(ladderEntry, fullPath.lastIndex + 1))


        return splitters.map {
            val part: List<PointPosition> = fullPath.subList(it.first, it.second)
            val partFloor = part.first().name.floor.toFloorsEnum()
            val partInfo: String = resolveFloorInfo(part.first().name)



            val pathDescription: String = if (splitters.count() == 1){
                "от аудитории $startClassroom пройдите в аудиторию $endClassroom"
            } else if(splitters.indexOf(it) == 0) {
                val nextfloor = fullPath[fullPath.indexOf(part.last()) + 1].name.floor
                val dir = if (part.last().name.floor > nextfloor) "спуститесь" else "поднимитесь"
                "от аудитории $startClassroom пройдите к лестнице и $dir на $nextfloor этаж"
            } else {
                "пройдите в аудиторию $endClassroom"
            }

            RoutePart(
                source = partFloor,
                sourceInfo = partInfo,
                pathPoints = part,
                description = pathDescription
            )


        }
    }



}