package com.example.universityproject.screens.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.drawable.toBitmap
import com.example.universityproject.model.ClickableArea
import com.example.universityproject.model.ClickableAreasList
import com.example.universityproject.model.Floors
import com.example.universityproject.model.getSpecifiedCoordinates
import com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment.MainBottomSheetInterface
import com.ortiz.touchview.TouchImageView

class MapView(
    context: Context,
    attributeSet: AttributeSet
    ) : TouchImageView(context, attributeSet) {

    private var floorMap: Bitmap = drawable.toBitmap()
    private lateinit var outputBitmap: Bitmap
    lateinit var fragment: MainBottomSheetInterface
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
    
    private fun loadClickableObjects(areas: Map<String, RectF>): List<ClickableArea> =
        areas.map {
            ClickableArea(
                it.value.getSpecifiedCoordinates(drawable.intrinsicWidth.toFloat(), drawable.intrinsicHeight.toFloat()),
                it.key,
                context,
                fragment,
                {clickableAreas.unselectAll()}
            ) {
                clickableAreas.unselectAll()
            }
        }


    fun checkClick(x: Float, y: Float) {
        val p = transformCoordTouchToBitmap(x, y, true)
        clickableAreas.checkClick(p)
        invalidate()
    }

}


