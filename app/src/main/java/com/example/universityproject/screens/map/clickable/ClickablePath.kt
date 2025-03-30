package com.example.universityproject.screens.map.clickable

import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import androidx.core.graphics.contains
import com.example.universityproject.model.getSpecifiedCoordinates

class ClickablePath(l: List<PointF>) : Path() {

    val bounds = RectF()

    init {
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

    fun contains(p: PointF) = bounds.contains(p)

    override fun close() {
        super.close()
        computeBounds(bounds, true)
    }

}