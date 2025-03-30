package com.example.data.datasources

import com.example.data.models.toFloorsEnum
import com.example.domain.models.AreaInfoItem
import com.example.domain.models.Floor
import com.example.domain.models.FloorAreas
import com.example.domain.models.alias.Floors
import com.example.domain.models.alias.GraphData
import com.example.domain.models.alias.Points
import com.example.domain.models.RoutePoint
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class ApiDataSource {

    private var client: HttpClient = open()


    private object Routes{

        private const val local = "http://10.0.2.2:8080"
        private const val network = "http://192.168.43.231:8080"

        private const val base = local
        const val DEFAULT = base
        const val CLASSROOMS = "$base/v2/allClassRooms"
        const val DATA = "$base/v2/data"
        const val COORDINATES = "$base/v2/coordinates"
        const val CLICKABLE_AREAS = "$base/v2/rooms"
    }


    private suspend fun allClassRooms(): Map<String, String> {
        return withContext(Dispatchers.IO) {
            client.get(Routes.CLASSROOMS).body()
        }
    }

    private suspend fun mappedData(): Map<String, List<String>> {
        return withContext(Dispatchers.IO) {
            client.get(Routes.DATA).body()
        }
    }

    private suspend fun pointsCoordinates(): Map<String, Pair<Float, Float>>{
        return withContext(Dispatchers.IO) {
            client.get(Routes.COORDINATES).body()
        }
    }

    private suspend fun rooms() : List<FloorAreas> {
        return withContext(Dispatchers.IO) {
            client.get(Routes.CLICKABLE_AREAS).body()
        }
    }


    fun makeFloorsRequest() : Flow<Floors> = request {
        Floors(
            rooms().associate { floor ->
                floor.num to Floor(
                    floor.num.toFloorsEnum(),
                    floor.rooms.map { room ->
                        AreaInfoItem(
                            RoutePoint(room.name, room.label),
                            room.points.map { Pair(it.first, it.second) })
                    }
                )
            }
        )
    }

    fun makeClassroomsRequest(): Flow<Map<String, String>> = request {
        allClassRooms()
    }

    fun makeRoutePointsRequest() : Flow<List<RoutePoint>> = request {
        rooms().flatMap {
            it.rooms.map { room ->
                RoutePoint(room.name, room.label)
            }
        }
    }

    fun makeGraphDataRequest(): Flow<GraphData> = request {
        GraphData(mappedData())
    }

    fun makeCoordinatesRequest(): Flow<Points> = request {
        Points(pointsCoordinates())
    }

    private fun <T> request(req: suspend () -> T): Flow<T> = flow {
        client = open()
        emit(req())
        client.close()
    }

    private fun open() = HttpClient(Android) {

        install(ContentNegotiation){
            json()
        }
    }



}