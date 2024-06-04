package com.example.universityproject

import android.content.Context
import android.graphics.PointF
import android.widget.Toast

class Model {

    private val points: MutableList<MapPoint> = mutableListOf()

    fun getLastPoint(): MapPoint{
        return points.last()
    }

    fun getAllPoints(): List<MapPoint>{
        return points
    }

    fun addNewPoint(p: PointF){
        val newPoint = MapPoint(p.x, p.y)
        points.add(newPoint)
    }

    fun ifPointTouched(touchX: Float, touchY: Float): MapPoint? {

        var point: MapPoint? = null

        points.forEach {
            if (
                (touchX >= it.xStart) and (touchX <= it.xEnd) and
                (touchY >= it.yStart) and (touchY <= it.yEnd)
            ) {
                point = it
            }
        }

        return point
    }

    companion object {
        val model = Model()
    }

}