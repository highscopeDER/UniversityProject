package com.example.universityproject.model

import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import androidx.core.graphics.contains

class ClickablePath : Path() {

    val bounds = RectF()

    fun contains(p: PointF) = bounds.contains(p)

    override fun close() {
        super.close()
        computeBounds(bounds, true)
    }

}