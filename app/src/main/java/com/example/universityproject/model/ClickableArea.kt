package com.example.universityproject.model

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import androidx.core.graphics.contains
import com.example.universityproject.R
import com.example.universityproject.route.RouteBuilder.Companion.resources
import com.example.universityproject.screens.bottomsheet.clickableAreaSelectionBottomSheetFragment.ClickableAreaSelectionBottomSheetDialog
import com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment.MainBottomSheetInterface

data class ClickableArea(
    val rect: RectF,
    val label: String,
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

    private val drawSelected = Paint().apply {
        color = colorSelected
        xfermode = mode
    }

    private val drawUnselected = Paint().apply {
        color = colorUnselected
        xfermode = mode

    }

    fun dispatchDrawing(canvas: Canvas) : Unit = canvas
        .drawRect(
            rect,
            if (selected) drawSelected else drawUnselected
        )

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
