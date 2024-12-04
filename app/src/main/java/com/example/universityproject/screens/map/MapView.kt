package com.example.universityproject.screens.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.drawable.toBitmap
import com.example.universityproject.model.AreaInfoItem
import com.example.universityproject.model.ClickableArea
import com.example.universityproject.model.ClickableAreasList
import com.example.universityproject.model.Floors
import com.example.universityproject.model.makeClickablePath
import com.ortiz.touchview.TouchImageView

class MapView(
    context: Context,
    attributeSet: AttributeSet
    ) : TouchImageView(context, attributeSet) {

    private var floorMap: Bitmap = drawable.toBitmap()
    private lateinit var outputBitmap: Bitmap
    lateinit var pathEdgesSetter: Pair<(item: String) -> Unit, (item: String) -> Unit>
    private val clickableAreas = ClickableAreasList(context)

    override fun onDraw(canvas: Canvas) {

        outputBitmap = floorMap.copy(Bitmap.Config.ARGB_8888, true)

        outputBitmap.applyCanvas {
            clickableAreas.draw(this)
        }
        setImageBitmap(outputBitmap)

        super.onDraw(canvas)
    }

    fun updateFloor(floor: Floors) {
        floorMap = ResourcesCompat.getDrawable(resources, floor.res, null)!!.toBitmap()
        clickableAreas.loadListOfAreas(
            loadClickableObjects(floor.areasInfo)
        )
        invalidate()
    }
    
    private fun loadClickableObjects(areas: List<AreaInfoItem>): List<ClickableArea> =
        areas.map {
            ClickableArea(
                it.points.makeClickablePath(floorMap.width.toFloat(), floorMap.height.toFloat()),
                it.name,
                it.icon,
                context,
                pathEdgesSetter,
                {clickableAreas.unselectAll()}
            ) {
                clickableAreas.unselectAll()
            }
        }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    fun checkClick(x: Float, y: Float) {
        val p = transformCoordTouchToBitmap(x, y, true)
        clickableAreas.checkClick(p)
        invalidate()
    }

}


