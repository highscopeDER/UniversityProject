package com.example.presentation.screens.map.clickable

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toRect
import com.example.domain.models.RoutePoint
import com.example.presentation.R
import com.example.presentation.screens.bottomsheet.clickableAreaSelectionBottomSheetFragment.ClickableAreaSelectionBottomSheetDialog
import com.example.presentation.screens.map.PathEdgesSetter

data class ClickableArea(
    val path: ClickablePath,
    val point: RoutePoint,
    val iconRes: Int?,
    val context: Context,
    val resources: Resources,
    val pathEdgesSetter: PathEdgesSetter,
    val bottomSheetOnDismiss: () -> Unit,
    val onClick: () -> Unit
) {

    private var selected: Boolean = false
    private val mode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
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

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 24f
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
        isLinearText = true

    }

    fun dispatchDrawing(canvas: Canvas) {
        canvas
            .drawPath(
                path,
                if (selected) drawSelected else drawUnselected
            )

        //canvas.drawText(point.label, path.bounds.centerX(), path.bounds.centerY(), textPaint)
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
