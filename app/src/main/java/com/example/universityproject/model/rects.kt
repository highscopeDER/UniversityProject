package com.example.universityproject.model

import android.graphics.PointF
import android.graphics.RectF

object rects {

    private val rect1 = RectF().apply {
        top = 20.301504f
        bottom = 13.7487840f
        left = 41.01538f
        right = 49.043537f
    }

    val pathWC = listOf(
        PointF(93.7416f,13.7496f),
        PointF(98.4535f,13.7496f),
        PointF(98.4535f,20.3001f),
        PointF(93.7416f,20.3001f),
    )

    val path1 = listOf(
        PointF(rect1.left, rect1.top),
        PointF(rect1.right, rect1.top),
        PointF(rect1.right, rect1.bottom),
        PointF(rect1.left, rect1.bottom)
    )

    val path8302 = listOf(
        PointF(88.2684f, 13.7496f),
        PointF(93.5916f, 13.7496f),
        PointF(93.5916f, 20.3001f),
        PointF(88.2684f, 20.3001f)
    )

    val path8303 = listOf(
        PointF(84.4671f,	13.7496f),
        PointF(88.1184f,	13.7496f),
        PointF(88.1184f,	20.3001f),
        PointF(84.4671f,	20.3001f)
    )


    val path8301 = listOf(
        PointF(104.3558f, 9.0998f),
        PointF(104.3558f, 8.2030f),
        PointF(111.8535f, 8.2030f),
        PointF(111.8535f, 20.8522f),
        PointF(103.2557f, 20.8522f),
        PointF(103.2557f, 9.0998f)
    )


    private val rect2 = RectF().apply {
        top = 20.301504f
        right = 40.865383f
        left = 31.4953650f
        bottom = 13.7487840f
    }

    val path2 = listOf(
        PointF(rect2.left, rect2.top),
        PointF(rect2.right, rect2.top),
        PointF(rect2.right, rect2.bottom),
        PointF(rect2.left, rect2.bottom)
    )

    private val rectFloor5 = RectF().apply {
        top = 6.8533f
        right = 14.3477f
        left = 6.4482f
        bottom = 0.8002f
    }

    val pathPoints = listOf(
        PointF(rectFloor5.left, rectFloor5.top),
        PointF(rectFloor5.right, rectFloor5.top),
        PointF(rectFloor5.right, rectFloor5.bottom),
        PointF(rectFloor5.left, rectFloor5.bottom),
    )









}