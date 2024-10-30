package com.example.universityproject.model

import com.example.universityproject.R

enum class Floors(
    val res: Int,
    val areasInfo: List<AreaInfoItem>
) {

    FLOOR_1(res = R.drawable.floor1, listOf()),
    FLOOR_2(res = R.drawable.floor2, listOf()),
    FLOOR_3(
        res = R.drawable.floor3,
        listOf(
            AreaInfoItem("8301", rects.path8301),
            AreaInfoItem("8302",rects.path8302),
            AreaInfoItem("8303",rects.path8303),
            AreaInfoItem("8308",rects.path1),
            AreaInfoItem("8309",rects.path2),
            AreaInfoItem("wc",rects.pathWC, R.drawable.ic_woman_wc)
        )
    ),
    FLOOR_4(res = R.drawable.floor4, listOf()),
    FLOOR_5(
        res = R.drawable.floor5,
        listOf(
            AreaInfoItem("8503", rects.pathPoints)
        )
    )


}