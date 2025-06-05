package com.example.presentation.screens.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.drawable.toBitmap
import com.example.domain.models.FloorsEnum
import com.example.domain.models.PointPosition
import com.example.presentation.model.bounds
import com.example.presentation.model.resource
import com.ortiz.touchview.TouchImageView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.min

class MapDrawnWithRoute(context: Context, attributeSet: AttributeSet) : TouchImageView(context, attributeSet) {

    private val floorMap = MutableStateFlow<FloorState>(FloorState.Empty)
    private val path = Path()
    private val paint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    private val pathBounds = RectF()

    init {
        minZoom = 1f
        maxZoom = 5f
    }

    fun updateFloor(floor: FloorsEnum, pathPoints: List<PointPosition>) {
        floorMap.value = FloorState.Floor(
            bitmap = ResourcesCompat.getDrawable(resources, floor.resource, null)!!.toBitmap(),
            bounds = floor.bounds
        )
        setupPath(pathPoints)
        setImageBitmap(
            ResourcesCompat.getDrawable(resources, floor.resource, null)!!.toBitmap().applyCanvas {
                drawPath(path, paint)
            },
        )
        invalidate()
        setZoom(pathBounds)
    }

    private fun setupPath(points: List<PointPosition>){

        val fm = floorMap.value as FloorState.Floor

        path.apply {
            reset()

            points.forEach {
                it.getSpecifiedCoordinates(fm.getWidth(), fm.getHeight()).apply {
                    if (it == points.first())  moveTo(this.x, this.y)
                    lineTo(this.x, this.y)
                }
            }

        }.computeBounds(pathBounds, true)

    }

    private fun PointPosition.getSpecifiedCoordinates(mapWidth: Float, mapHeight: Float): PointF {

        val fm = floorMap.value as FloorState.Floor


        val length = fm.bounds.first
        val height = fm.bounds.second

        val x_percent = x / length
        val y_percent = (height - y) / height

        return PointF(
            mapWidth * x_percent,
            mapHeight * y_percent
        )
    }


    private fun setZoom(bounds: RectF) {

        val fm = floorMap.value as FloorState.Floor

        val w = fm.getWidth()
        val h = fm.getHeight()

        val sX = w / bounds.width()
        val sY = h / bounds.height()

        val fX = bounds.centerX() / w
        val fY = bounds.centerY() / h

        setZoom(min(sX, sY) * 0.9f, fX, fY)

    }


    sealed class FloorState{
        data object Empty: FloorState()
        data class Floor(val bitmap: Bitmap, val bounds: Pair<Float, Float>): FloorState() {
            fun getWidth(): Float = bitmap.width.toFloat()
            fun getHeight(): Float = bitmap.height.toFloat()
        }
    }


}