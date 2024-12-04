package com.example.universityproject.route

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.drawable.toBitmap
import com.example.universityproject.api.API
import com.example.universityproject.dijkstra.Dijkstra
import com.example.universityproject.dijkstra.PointPosition
import com.example.universityproject.model.Floors
import com.example.universityproject.model.building
import com.example.universityproject.model.floor
import com.example.universityproject.model.isLadder

class RouteBuilder {

    private var fullPath: List<PointPosition> = listOf()
    private lateinit var startClassroom: String
    private lateinit var endClassroom: String

    private val paint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    private fun resolveFloorInfo(pointName: String): String {
        return "${pointName.building} корпус, ${pointName.floor} этаж"
    }

    private fun getFullRoute(s: String, e: String) {
        startClassroom = s
        endClassroom = e
        fullPath = Dijkstra.algorithm.buildRoute(s, e)
    }

    private fun splitByFloors(): List<RouteStep> {

        var ladderEntry: Int = 0
        var count: Boolean = false
        val splitters: MutableList<Pair<Int, Int>> = mutableListOf()

        val resultList: MutableList<RouteStep> = mutableListOf()

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



        splitters.forEach {
            val part: List<PointPosition> = fullPath.subList(it.first, it.second)
            val partInfo: String = resolveFloorInfo(part.first().name)
            val pathBounds = RectF()


            val pathDescription: String = if (splitters.count() == 1){
                "от аудитории $startClassroom пройдите в аудиторию $endClassroom"
            } else if(splitters.indexOf(it) == 0) {
                val nextfloor = fullPath[fullPath.indexOf(part.last()) + 1].name.floor
                val dir = if (part.last().name.floor > nextfloor) "спуститесь" else "поднимитесь"
                "от аудитории $startClassroom пройдите к лестнице и $dir на $nextfloor этаж"
            } else {
                "пройдите в аудиторию $endClassroom"
            }

            //TODO: отредачить с учетом новых точек интереса


            val floor: Floors? = when (part.first().name.floor) {
                1 -> Floors.FLOOR_1
                2 -> Floors.FLOOR_2
                3 -> Floors.FLOOR_3
                4 -> Floors.FLOOR_4
                5 -> Floors.FLOOR_5
                else -> null
            }

            if (floor != null) {

                val path: Path = Path()
                path.reset()
                var start = true

                val options: BitmapFactory.Options = BitmapFactory.Options()
                options.inJustDecodeBounds = true

                val bitmap = ResourcesCompat.getDrawable(resources, floor.res, null)?.toBitmap()

                if (bitmap != null) {
                    path.apply {
                        part.forEach { point ->
                            val p = point.getSpecifiedCoordinates(bitmap.width.toFloat(), bitmap.height.toFloat())
                            if (start) {
                                moveTo(p.x, p.y)
                            }
                            lineTo(p.x, p.y)
                            start = false
                        }
                    }

                    path.computeBounds(pathBounds, true)

                    val newBitmap = bitmap.applyCanvas {
                        drawPath(path, paint)
                    }

                    resultList.add(RouteStep(newBitmap, partInfo, pathDescription, pathBounds))
                }
            }
        }

        return resultList
    }


    companion object {
        private val builder = RouteBuilder()

        fun buildRoute(start: String, end: String): List<RouteStep> {
            builder.getFullRoute(start, end)
            return builder.splitByFloors()
        }

        lateinit var resources: Resources

    }

}