package com.example.universityproject.dijkstra

import com.example.universityproject.api.API
import kotlin.math.pow
import kotlin.math.sqrt

class Dijkstra () {

    lateinit var graph: Graph<String>
    private lateinit var pointsList: Map<String, String>
    private lateinit var data: Map<String, List<String>>
    private lateinit var coordinates: Map<String, Pair<Float, Float>>

    private fun <String> dijkstra(graph: Graph<String>, start: String): Map<String, String?> {
        val S: MutableSet<String> = mutableSetOf() // a subset of vertices, for which we know the true distance

        val delta: MutableMap<String, Float> = graph.vertices.map { it to Float.MAX_VALUE }.toMap().toMutableMap()
        delta[start] = 0f

        val previous: MutableMap<String, String?> = graph.vertices.map { it to null }.toMap().toMutableMap()


        while (S != graph.vertices) {
            val v: String = delta
                .filter { !S.contains(it.key) }
                .minBy {  it.value }
                .key

            graph.edges.getValue(v).minus(S).forEach { neighbor ->
                val newPath = delta.getValue(v) + graph.weights.getValue(Pair(v, neighbor))

                if (newPath < delta.getValue(neighbor)) {
                    delta[neighbor] = newPath
                    previous[neighbor] = v
                }

            }


            S.add(v)
        }

        return previous.toMap()
    }

    private fun <String> shortestPath(shortestPathTree: Map<String, String?>, end: String): List<String> {

        fun pathTo(end: String): List<String> {
            if (shortestPathTree[end] == null) return listOf(end)
            return listOf(pathTo(shortestPathTree[end]!!), listOf(end)).flatten()
        }

        return pathTo(end)
    }

    private fun useDijkstra(graph: Graph<String>, start: String, end: String) : List<String> =
        shortestPath(
            dijkstra(graph, start),
            end
        )

    fun buildGraph(){

        pointsList = API.dbApi.getAllClassRooms().associate { it.name to it.label }
        data = API.dbApi.getAlgorithmData()
        coordinates = API.dbApi.getAllPointsCoordinates()

        val vertices: Set<String> = data.keys
        val edges: Map<String, List<String>> = data
        val weights: MutableMap<Pair<String, String>, Float> = mutableMapOf()

        data.forEach {

            val firstPoint = coordinates[it.key]
            it.value.forEach { item ->
                val secondPoint = coordinates[item]
                val a = Pair(it.key, item)
                val b = sqrt(
                    (secondPoint!!.first - firstPoint!!.first).pow(2)
                            +
                            (secondPoint.second - firstPoint.second).pow(2)
                )

                weights[a] = b
            }
        }
        graph = Graph(vertices, edges, weights)
    }

    fun buildRoute(startPoint: String, endPoint: String) :  List< PointPosition > {

        val s = pointsList[startPoint]
        val e = pointsList[endPoint]

        var path: List<String> = useDijkstra(
            graph,
            startPoint,
            endPoint
        )

        if (path.first() != s || path.last() != e) {
            path = useDijkstra(graph, endPoint, startPoint)
            path = path.reversed()
        }

        println(path)

        val mappedPath: MutableList< PointPosition > = mutableListOf()

        path.forEach {
            coordinates[it]?.let { coord ->
                mappedPath.add(
                    PointPosition (
                        it,
                        coord.first,
                        coord.second
                    )
                )
            }
        }

            return mappedPath
    }

    companion object{
        val algorithm = Dijkstra()
    }

}

