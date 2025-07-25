package com.example.presentation.core.components.map.models

import android.graphics.PointF
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

fun Path.createFromPointsList(l: List<PointF>): Path = this.apply {
    var first = true

    l.forEach {
        if (first) {
            moveTo(it.x, it.y)
        }
        lineTo(it.x, it.y)
        first = false
    }

    close()

}

fun Path.containsInBounds(p: Offset) = getBounds().contains(p)