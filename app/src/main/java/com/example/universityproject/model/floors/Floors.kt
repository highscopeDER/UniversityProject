package com.example.universityproject.model.floors

import com.example.universityproject.screens.map.clickable.AreaInfoItem

data class Floors(
    val res: Int,
    val areasInfo: List<AreaInfoItem>
) {

    companion object {
        val floors: MutableMap<Int, Floors> = mutableMapOf()
    }
}