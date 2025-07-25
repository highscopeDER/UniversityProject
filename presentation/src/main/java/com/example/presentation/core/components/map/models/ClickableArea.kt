package com.example.presentation.core.components.map.models

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.domain.models.RoutePoint

data class ClickableArea(
    val path: Path,
    val point: RoutePoint,
    val colorSelected: Color = Color.Blue,
    val colorUnselected: Color = Color.Yellow,
    val onClick: () -> Unit
) {

    private var selected: Boolean = false

    fun draw(drawScope: DrawScope) {
        drawScope.drawPath(
                path = path,
                color = if (selected) colorSelected else colorUnselected,
                blendMode = BlendMode.SrcOver
            )
    }

    fun checkIfClicked(point: Offset){
        if (path.containsInBounds(point)) {
            performClick()
        }
    }

    private fun performClick(){
        onClick()
        selected = !selected
    }

    fun unselect(){
        selected = false
    }

}
