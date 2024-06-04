package com.example.universityproject

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

val TAG = "DEBUGGING APP"

class MapView(context: Context, attributeSet: AttributeSet, ) : TouchImageView(context, attributeSet) {

    private var mapWidth: Int
    private var mapHeight: Int
    private var prevPoint: PointF? = null

    private var drawingPath: Boolean = false

    private var horizontalScale: Float
    private var verticalScale: Float
    private var matrix: Matrix
    private var defBitmap: Bitmap

    private var pointsListOnFloorChange: List<Pair<Float, Float>>? = null
    private var destinationFloor: Int? = null

    //private val defBitmap = drawable.toBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888)
    private var bitmap: Bitmap

    private var resultBitmap: Bitmap

    private var resultCanvas: Canvas
    private var drawingCanvas: Canvas
    private val paint = Paint()
        .apply {
            strokeWidth = 3f
            style = Paint.Style.STROKE
            color = Color.BLUE
        }
    val path = Path()

    init {

        mapWidth = drawable.intrinsicWidth
        mapHeight = drawable.intrinsicHeight

        defBitmap = drawable.toBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888)
        bitmap = Bitmap.createBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888)
        resultBitmap = Bitmap.createBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888)
        drawingCanvas = Canvas(bitmap)
        resultCanvas = Canvas(resultBitmap)

        setImageBitmap(defBitmap)
        val pt = transformCoordTouchToBitmap(mapWidth.toFloat(), mapHeight.toFloat(), true)
        horizontalScale = mapWidth.toFloat() / pt.x
        verticalScale = mapHeight.toFloat() / pt.y
        matrix = Matrix()
            .apply {
                reset()
                setScale(horizontalScale, verticalScale)
            }


    }


//    fun setNewPoint(x: Float, y: Float){
//        val point = transformCoordTouchToBitmap(x, y, true)
//        Model.model.addNewPoint(point)
//        val drawablePoint = Model.model.getLastPoint()
//        path.apply {
//            reset()
//            moveTo(drawablePoint.xStart, drawablePoint.yStart)
//            lineTo(drawablePoint.xStart, drawablePoint.yEnd)
//            moveTo(drawablePoint.xStart, drawablePoint.yEnd)
//            lineTo(drawablePoint.xEnd, drawablePoint.yEnd)
//            moveTo(drawablePoint.xEnd, drawablePoint.yEnd)
//            lineTo(drawablePoint.xEnd, drawablePoint.yStart)
//            moveTo(drawablePoint.xEnd, drawablePoint.yStart)
//            lineTo(drawablePoint.xStart, drawablePoint.yStart)
//            transform(matrix)
//        }
//
//        canvas
//            .apply {
//                drawPoint(drawablePoint.x, drawablePoint.y, paint)
//                drawPath(path, paint)
//            }
//
//        setImageBitmap(bitmap)
//
//    }
//
//    fun draw(x: Float, y: Float){
//
//        //Log.d(TAG, "v: $verticalScale, h: $horizontalScale")
//        //Log.d(TAG, drawable.bounds.toString())
//
//        val point = transformCoordTouchToBitmap(x, y, true)
//
//        val cx = point.x
//        val cy = point.y
//
//        var msg: String = ""
//
//        if(prevPoint != null){
//
//            val lastX = prevPoint!!.x
//            val lastY = prevPoint!!.y
//
//            path
//                .apply {
//                    reset()
//                    moveTo(lastX, lastY)
//                    lineTo(cx, cy)
//                    msg = "from ($lastX, $lastY) to ($cx, $cy)"
//                }
//
//        } else {
//            path
//                .apply {
//                    reset()
//                    moveTo(cx, cy)
//                    msg = "start point set at ($cx, $cy)"
//                }
//        }
//
//        Log.d(TAG, msg)
//
//        prevPoint = point
//        path.transform(matrix)
//        canvas.drawPath(path, paint)
//        setImageBitmap(bitmap)
//    }

    fun updateDrawable(floorNum: Int){
        mapWidth = drawable.intrinsicWidth
        mapHeight = drawable.intrinsicHeight

        defBitmap = drawable.toBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888)
        bitmap = drawable.toBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888)
        drawingCanvas = Canvas(bitmap)

        setImageBitmap(defBitmap)
        val pt = transformCoordTouchToBitmap(mapWidth.toFloat(), mapHeight.toFloat(), true)
        horizontalScale = mapWidth.toFloat() / drawable.bounds.width()
        verticalScale = mapHeight.toFloat() / drawable.bounds.height()
        matrix = Matrix()
            .apply {
                reset()
                setScale(horizontalScale, verticalScale)
            }

        prevPoint = null

        if (destinationFloor != null && destinationFloor == floorNum) {
            drawPathByListOfPoints(pointsListOnFloorChange!!, null, null)
        }

        pointsListOnFloorChange = null
        destinationFloor = null

    }

    fun drawPathByListOfPoints(list: List<Pair<Float,Float>>, ladderId: Int?, ref: Int?) {

        var isStart = true
        path.reset()

        val listOfPathCoordinates = if (ladderId != null) {
            destinationFloor = ref!!
            pointsListOnFloorChange = list.subList(ladderId + 1, list.size)
            val temp: List<Pair<Float, Float>> = list.subList(0, ladderId + 1)
            temp
        } else {
            list
        }

        listOfPathCoordinates.forEach {
            val point = it.toPoint()
            path.apply {
                if (!isStart) lineTo(point.x, point.y) else isStart = false
                moveTo(point.x, point.y)
            }
        }

        path.transform(matrix)
        bitmap = Bitmap.createBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888).applyCanvas {
            drawPath(path, paint)
        }

        resultBitmap = Bitmap.createBitmap(mapWidth, mapHeight, Bitmap.Config.ARGB_8888).applyCanvas {
            drawBitmap(defBitmap, matrix, paint)
            drawBitmap(bitmap, matrix, paint)
        }

        setImageBitmap(resultBitmap)

    }

    private fun Pair<Float, Float>.toPoint(): PointF{

        val length = 112.5f
        val height = 29.9f

        val x_percent = first / length
        val y_percent = (height - second) / height

        return PointF(
            mapWidth.toFloat() * x_percent,
            mapHeight.toFloat() * y_percent
        )
    }

}