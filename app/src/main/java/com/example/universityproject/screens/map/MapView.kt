package com.example.universityproject.screens.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.drawable.toBitmap
import com.example.domain.models.Floor
import com.example.universityproject.model.resource
import com.example.universityproject.screens.map.clickable.ClickableArea
import com.example.universityproject.screens.map.clickable.ClickableAreasList
import com.example.universityproject.screens.map.clickable.ClickablePath
import com.ortiz.touchview.TouchImageView

class MapView(
    context: Context,
    attributeSet: AttributeSet
    ) : TouchImageView(context, attributeSet) {

    private var floorMap: Bitmap = drawable.toBitmap()
    private lateinit var outputBitmap: Bitmap
    lateinit var pathEdgesSetter: PathEdgesSetter
    private val clickableAreas = ClickableAreasList()

    init {
        minZoom = 1f
        maxZoom = 5f
        setZoom(1f)
        doubleTapScale = 1f
    }

    override fun onDraw(canvas: Canvas) {

        outputBitmap = floorMap.copy(Bitmap.Config.ARGB_8888, true)

        outputBitmap.applyCanvas {
            clickableAreas.draw(this)
        }

        setImageBitmap(outputBitmap)

        super.onDraw(canvas)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }


    fun updateFloor(floor: Floor) {
        floorMap = ResourcesCompat.getDrawable(resources, floor.enum.resource, null)!!.toBitmap()
        clickableAreas.loadListOfAreas(
            floor.getClickableAreas(
                context,
                floorMap.width.toFloat(),
                floorMap.height.toFloat(),
                pathEdgesSetter,
                { clickableAreas.unselectAll() }
            )
        )

        invalidate()
    }

    fun checkClick(x: Float, y: Float) {
        val p = transformCoordTouchToBitmap(x, y, true)
        clickableAreas.checkClick(p)
        invalidate()
    }

    private fun Floor.getClickableAreas (
        context: Context,
        mapWidth: Float,
        mapHeight: Float,
        pathEdgesSetter: PathEdgesSetter,
        unit: () -> Unit
    ): List<ClickableArea> =
        areasInfo.map {
            ClickableArea(
                ClickablePath(it.points.map { p -> getSpecifiedCoordinates(PointF(p.first, p.second), mapWidth, mapHeight) }),
                it.point,
                it.icon,
                context,
                resources,
                pathEdgesSetter,
                { unit() },
                { unit() }
            )
        }

    private fun Floor.getSpecifiedCoordinates(p: PointF, mapWidth: Float, mapHeight: Float): PointF {
        val length = bounds.first
        val height = bounds.second

        val x_percent = p.x / length
        val y_percent = (height - p.y) / height

        return PointF(
            mapWidth * x_percent,
            mapHeight * y_percent
        )
    }

}




