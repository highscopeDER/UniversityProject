package com.example.universityproject.model

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toRect
import com.example.universityproject.R
import com.example.universityproject.route.RouteBuilder.Companion.resources
import com.example.universityproject.screens.bottomsheet.clickableAreaSelectionBottomSheetFragment.ClickableAreaSelectionBottomSheetDialog
import com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment.MainBottomSheetInterface

data class ClickableArea(
    val rect: ClickablePath,
    val label: String,
    val iconRes: Int?,
    val context: Context,
    val fragment: MainBottomSheetInterface,
    val bottomSheetOnDismiss: () -> Unit,
    val onClick: () -> Unit
) {

    private var selected: Boolean = false
    private val mode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
    private val colorSelected = resources.getColor(R.color.color_selected, null)
    private val colorUnselected = resources.getColor(R.color.color_unselected, null)
    private val bottomSheet = ClickableAreaSelectionBottomSheetDialog(context, fragment, label, onClick)
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
                rect,
                if (selected) drawSelected else drawUnselected
            )
        icon?.bounds = Rect().apply{
            val b = rect.bounds.toRect()
            left = b.centerX() - b.width() / 4
            top = b.centerY() - b.height() / 4
            right = b.centerX() + b.width() / 4
            bottom = b.centerY() + b.height() / 4
        }
        icon?.draw(canvas)
    }
    fun checkIfClicked(point: PointF){
        if (rect.contains(point)) {
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




}
