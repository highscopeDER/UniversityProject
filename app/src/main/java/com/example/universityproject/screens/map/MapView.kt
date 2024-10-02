package com.example.universityproject.screens.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.drawable.toBitmap
import com.ortiz.touchview.TouchImageView

open class MapView(context: Context, attributeSet: AttributeSet) : TouchImageView(context, attributeSet) {

    private var mapWidth: Int = 0
    private var mapHeight: Int = 0

    private var horizontalScale: Float = 0.0f
    private var verticalScale: Float = 0.0f
    private lateinit var matrix: Matrix

    private lateinit var bitmap: Bitmap

    init {
        //updateDrawable()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        updateDrawable()
    }


    fun updateDrawable(){
        mapWidth = drawable.intrinsicWidth
        mapHeight = drawable.intrinsicHeight

        bitmap = drawable.toBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888)

        setImageBitmap(bitmap)
        val pt = transformCoordTouchToBitmap(mapWidth.toFloat(), mapHeight.toFloat(), true)
        horizontalScale = mapWidth.toFloat() / drawable.bounds.width()
        verticalScale = mapHeight.toFloat() / drawable.bounds.height()
        Log.d("TAG", "scale^ $horizontalScale, $verticalScale")
        matrix = Matrix()
            .apply {
                reset()
                setScale(horizontalScale, verticalScale)
            }
    }

//    val path = Path()
//    fun drawPathByListOfPoints(list: List<Pair<Float,Float>>, ladderId: Int?, ref: Int?) {
//
//        var isStart = true
//        path.reset()
//
//        val listOfPathCoordinates = if (ladderId != null) {
//            destinationFloor = ref!!
//            pointsListOnFloorChange = list.subList(ladderId + 1, list.size)
//            val temp: List<Pair<Float, Float>> = list.subList(0, ladderId + 1)
//            temp
//        } else {
//            list
//        }
//
//        listOfPathCoordinates.forEach {
//            val point = it.toPoint()
//            path.apply {
//                if (!isStart) lineTo(point.x, point.y) else isStart = false
//                moveTo(point.x, point.y)
//            }
//        }
//
//        path.transform(matrix)
//        bitmap = Bitmap.createBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888).applyCanvas {
//            drawPath(path, paint)
//        }
//        setImageBitmap(bitmap)
//    }

       // paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))


//    private fun Pair<Float, Float>.toPoint(): PointF{
//
//        val length = 112.5f
//        val height = 29.9f
//
//        val x_percent = first / length
//        val y_percent = (height - second) / height
//
//        return PointF(
//            mapWidth.toFloat() * x_percent,
//            mapHeight.toFloat() * y_percent
//        )
//    }

}