package com.example.universityproject.dijkstra

import android.util.Log
import com.example.universityproject.api.API
import kotlin.math.pow
import kotlin.math.sqrt

class Dijkstra () {

    private fun <T> dijkstra(graph: Graph<T>, start: T): Map<T, T?> {
        val S: MutableSet<T> = mutableSetOf() // a subset of vertices, for which we know the true distance

        val delta = graph.vertices.map { it to Int.MAX_VALUE }.toMap().toMutableMap()
        delta[start] = 0

        val previous: MutableMap<T, T?> = graph.vertices.map { it to null }.toMap().toMutableMap()

        while (S != graph.vertices) {
            val v: T = delta
                .filter { !S.contains(it.key) }
                .minBy { it.value }
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

    private fun <T> shortestPath(shortestPathTree: Map<T, T?>, start: T, end: T): List<T> {
        fun pathTo(start: T, end: T): List<T> {
            if (shortestPathTree[end] == null) return listOf(end)
            return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
        }

        return pathTo(start, end)
    }

    private fun useDijkstra(graph: Graph<String>, start: String, end: String) : List<String> =
        shortestPath(
            dijkstra(graph, start),
            start,
            end
        )


    fun buildRoute(startPoint: String, endPoint: String) :  List< PointPosition > {
        val data: Map<String, List<String>> = API.dbApi.getAlgorithmData()
        val coordinates: Map<String, Pair<Float, Float>> = API.dbApi.getAllPointsCoordinates()
        val pointsList = API.dbApi.getAllClassRooms()

        val vertices: Set<String> = data.keys
        val edges: MutableMap<String, Set<String>> = mutableMapOf()
        val weights: MutableMap<Pair<String, String>, Int> = mutableMapOf()

        Log.d("TAG", coordinates.toString())

        data.forEach {
            edges[it.key] = it.value.toSet()

            val firstPoint = coordinates[it.key]

            it.value.forEach { item ->
                val secondPoint = coordinates[item]
                val a = Pair(it.key, item)


                val b = sqrt(
                    (secondPoint!!.first - firstPoint!!.first).pow(2) + (secondPoint.second - firstPoint.second).pow(2)
                ).toInt()

                weights[a] = b
            }
        }
        val graph: Graph<String> = Graph(vertices, edges, weights)



        val pathEdges = Pair(startPoint, endPoint)

        val start = minOf(pathEdges.first, pathEdges.second)
        val end: String = maxOf(pathEdges.first, pathEdges.second)

        val s = pointsList[start]
        val e = pointsList[end]

        println("start point: $s, end point: $e")

        val path: List<String> = useDijkstra(
            graph,
            minOf(s!!, e!!),
            maxOf(s, e))


        println("FIRST $path")

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

