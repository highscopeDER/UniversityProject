package com.example.universityproject.api

import android.util.Log
import com.example.universityproject.dijkstra.Dijkstra
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class API {

    private var list: Map<String, String> = mapOf()
    private var algorithmData: Map<String, List<String>> = mapOf()
    private var pointsCoord: Map<String, Pair<Float, Float>> = mapOf()

    private var client = HttpClient(Android) {

        install(ContentNegotiation){
            json()
        }
    }

    fun initialize() {}


    init {
        Log.d("API", "init called")
        CoroutineScope(Dispatchers.IO).launch {
            coroutineScope {
                list = allClassRooms()
                algorithmData = mappedData()
                pointsCoord = pointsCoordinates()

            }
            client.close()
            Dijkstra.algorithm.buildGraph()
        }
    }


    // 192.168.43.231
    // "http://10.0.2.2:8080"

    private object Routes{

        private const val local = "http://10.0.2.2:8080"
        private const val network = "http://192.168.43.231:8080"


        private const val base = local
        const val DEFAULT = base
        const val ALL_ROOMS = "$base/allClassRooms"
        const val DATA = "$base/data"
        const val COORD = "$base/coordinates"
    }


    private suspend fun allClassRooms(): Map<String, String> {
        return withContext(Dispatchers.IO) {
            client.get(Routes.ALL_ROOMS).body()
        }
    }

    private suspend fun mappedData(): Map<String, List<String>> {
        return withContext(Dispatchers.IO) {
            client.get(Routes.DATA).body()
        }
    }

    private suspend fun pointsCoordinates(): Map<String, Pair<Float, Float>>{
        return withContext(Dispatchers.IO) {
            client.get(Routes.COORD).body()
        }
    }

    fun getAllClassRooms() : Map<String, String> = list

    fun getAlgorithmData(): Map<String, List<String>> = algorithmData

    fun getAllPointsCoordinates(): Map<String, Pair<Float, Float>> = pointsCoord

    fun getPointCoordinates(point: String): Pair<Float, Float>? = pointsCoord[point]


    companion object {
        val dbApi = API()
    }

}


