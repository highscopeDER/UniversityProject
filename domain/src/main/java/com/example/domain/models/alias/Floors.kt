package com.example.domain.models.alias

import com.example.domain.models.Floor

class Floors (val map: Map<Int, Floor>) {

    fun getFloor(index: Int): Floor = map.getOrDefault(index, map.values.first())

}