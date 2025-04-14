package com.example.universityproject.screens.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
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
import com.example.universityproject.model.resource
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
        println("mapview init")
    }

    override fun onDraw(canvas: Canvas) {



        if (floorMap.value is FloorState.Floor ) {

            val outputBitmap = (floorMap.value as FloorState.Floor).bitmap.copy(Bitmap.Config.ARGB_8888, true)

            outputBitmap.applyCanvas {
                drawPath(path, paint)
            }

            setImageBitmap(outputBitmap)

        }

        super.onDraw(canvas)
    }



    fun updateFloor(floor: FloorsEnum, pathPoints: List<PointPosition>) {
        floorMap.value = FloorState.Floor(ResourcesCompat.getDrawable(resources, floor.resource, null)!!.toBitmap())
        setupPath(pathPoints)
        invalidate()
    }

    private fun setupPath(points: List<PointPosition>){

        val fm = floorMap.value as FloorState.Floor

        path.apply {
            reset()

            points.forEach { point ->
                val p = point.getSpecifiedCoordinates(fm.getWidth(), fm.getHeight())
                if (point == points.first()) {
                    moveTo(p.x, p.y)
                }
                lineTo(p.x, p.y)
            }
        }.computeBounds(pathBounds, true)
        setZoom(pathBounds)

    }

    private fun PointPosition.getSpecifiedCoordinates(mapWidth: Float, mapHeight: Float): PointF {
        val bounds = Pair(112.753f, 29.9f)

        val length = bounds.first
        val height = bounds.second

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
        data class Floor(val bitmap: Bitmap): FloorState() {
            fun getWidth(): Float = bitmap.width.toFloat()
            fun getHeight(): Float = bitmap.height.toFloat()
        }
    }


}