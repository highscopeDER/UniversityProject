package com.example.universityproject.model

import android.graphics.RectF
import com.example.universityproject.R

enum class Floors(
    val res: Int,
    val areasInfo: Map<String, RectF>
) {

    FLOOR_1(res = R.drawable.floor1, mapOf()),
    FLOOR_2(res = R.drawable.floor2, mapOf()),
    FLOOR_3(
        res = R.drawable.floor3_new,
        mapOf(
            "8308" to rects.rect1,
            "8309" to rects.rect2
        )
    ),
    FLOOR_4(res = R.drawable.floor4, mapOf()),
    FLOOR_5(res = R.drawable.floor5_colored, mapOf())

}