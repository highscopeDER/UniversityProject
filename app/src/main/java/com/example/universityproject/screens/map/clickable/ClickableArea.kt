package com.example.universityproject.screens.map.clickable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toRect
import com.example.universityproject.R
import com.example.universityproject.model.RoutePoint
import com.example.universityproject.route.RouteBuilder.Companion.resources
import com.example.universityproject.screens.bottomsheet.clickableAreaSelectionBottomSheetFragment.ClickableAreaSelectionBottomSheetDialog

data class ClickableArea(
    val path: ClickablePath,
    //val label: String,
    val point: RoutePoint,

    val iconRes: Int?,
    val context: Context,
    val pathEdgesSetter: Pair<(item: RoutePoint) -> Unit, (item: RoutePoint) -> Unit>,
    val bottomSheetOnDismiss: () -> Unit,
    val onClick: () -> Unit
) {

    private var selected: Boolean = false
    private val mode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
    private val colorSelected = resources.getColor(R.color.color_selected, null)
    private val colorUnselected = resources.getColor(R.color.color_unselected, null)
    private val bottomSheet = ClickableAreaSelectionBottomSheetDialog(context, pathEdgesSetter, point, bottomSheetOnDismiss)
    private val icon = iconRes?.let { ResourcesCompat.getDrawable(resources, it, null) }

    private val drawSelected = Paint().apply {
        color = colorSelected
        style = Paint.Style.FILL
        xfermode = mode
    }

    private val drawUnselected = Paint().apply {
        color = colorUnselected
        style = Paint.Style.FILL
        xfermode = mode

    }

    fun dispatchDrawing(canvas: Canvas) {
        canvas
            .drawPath(
                path,
                if (selected) drawSelected else drawUnselected
            )
        drawIcon(canvas)
    }



    private fun drawIcon(canvas: Canvas){
        icon?.bounds = Rect().apply{
            val b = path.bounds.toRect()
            left = b.centerX() - iconSize
            top = b.centerY() - iconSize
            right = b.centerX() + iconSize
            bottom = b.centerY() + iconSize
        }
        icon?.draw(canvas)

    }

    fun checkIfClicked(point: PointF){
        if (path.contains(point)) {
            performClick()
        }
    }

    private fun performClick(){
        onClick()
        selected = !selected
        showSelectionFragment()
    }

    fun unselect(){
        selected = false
    }

    private fun showSelectionFragment(){

        bottomSheet.show()
    }


    companion object {
        private const val iconSize = 25
    }

}
