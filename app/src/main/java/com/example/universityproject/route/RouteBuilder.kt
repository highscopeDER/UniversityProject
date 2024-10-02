package com.example.universityproject.route

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.example.universityproject.dijkstra.Dijkstra
import com.example.universityproject.dijkstra.PointPosition
import com.example.universityproject.screens.Floors

class RouteBuilder {

    private var fullPath: List<PointPosition> = listOf()

    private val paint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    private fun resolveFloorInfo(pointName: String): String {
      return "8 корпус, ${pointName[1]} этаж"
    }

    private fun getFullRoute(s: String, e: String) {
        fullPath = Dijkstra.algorithm.buildRoute(s, e)
    }

    private fun splitByFloors(): List<RouteStep> {

        var ladderEntry: Int = 0
        var count: Boolean = false
        val splitters: MutableList<Pair<Int, Int>> = mutableListOf()

        val resultList: MutableList<RouteStep> = mutableListOf()

        fullPath.forEach { item ->
            if (item.name.last() == 'l') {
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

            val floor: Floors? = when (part.first().name[1].digitToInt()) {
                3 -> Floors.FLOOR_3
                4 -> Floors.FLOOR_4
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
                            if (!start) {
                                lineTo(p.x, p.y)
                            }
                            moveTo(p.x, p.y)
                            start = false
                        }
                    }

                    val newbm = bitmap.applyCanvas {
                        drawPath(path, paint)
                    }

                    resultList.add(RouteStep(newbm, partInfo))
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