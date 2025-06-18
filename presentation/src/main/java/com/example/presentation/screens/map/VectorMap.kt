package com.example.presentation.screens.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.drawable.VectorDrawable
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.times
import androidx.core.graphics.toRectF
import com.example.domain.models.Floor
import com.example.presentation.model.bounds
import com.example.presentation.model.resource
import com.example.presentation.screens.map.clickable.ClickableArea
import com.example.presentation.screens.map.clickable.ClickableAreasList
import com.example.presentation.screens.map.clickable.ClickablePath

class VectorMap (context: Context, attributeSet: AttributeSet) : AppCompatImageView(context, attributeSet) {

    private var viewMatrix: Matrix = Matrix()

    private var redrawNeeded: Boolean = false
    private var floorMap: VectorDrawable? = null

    private var viewBounds: Rect = Rect()

    lateinit var pathEdgesSetter: PathEdgesSetter
    private val clickableAreas = ClickableAreasList()
    
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        viewBounds.left = left
        viewBounds.top = top
        viewBounds.right = right
        viewBounds.bottom = bottom
    }
    
    override fun onDraw(canvas: Canvas) {
        if (redrawNeeded) {

            floorMap?.apply {

                canvas.concat(viewMatrix)
                draw(canvas)

                clickableAreas.draw(canvas)
            }

        }

        super.onDraw(canvas)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun redrawView(){
        redrawNeeded = true
        invalidate()
    }


    fun updateFloor(floor: Floor) {
        floorMap = AppCompatResources.getDrawable(this.context, floor.enum.resource) as VectorDrawable

        floorMap!!.apply {

            bounds = Rect(0, 0, intrinsicWidth, intrinsicHeight)

            viewMatrix.setRectToRect(
                bounds.toRectF(),
                viewBounds.toRectF(),
                Matrix.ScaleToFit.CENTER
            )

        }

        clickableAreas.loadListOfAreas(
            floor.getClickableAreas(
                context,
                floorMap!!.intrinsicWidth.toFloat(),
                floorMap!!.intrinsicHeight.toFloat(),
                pathEdgesSetter,
                { clickableAreas.unselectAll(); redrawView() }
            )
        )

        redrawView()
    }

    fun checkClick(x: Float, y: Float) {
        val p = PointF(x, y)
        TODO("need to use view matrix to translate coord from screen dim to image dim")
        println(p)
        clickableAreas.checkClick(p)
        redrawView()
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

        val bounds = enum.bounds

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