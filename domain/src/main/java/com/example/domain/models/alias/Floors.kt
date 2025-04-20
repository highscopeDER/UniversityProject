package com.example.domain.models.alias

import com.example.domain.models.Floor
import com.example.domain.models.FloorsEnum

class Floors (val map: Map<FloorsEnum, Floor>) {

    fun getFloor(index: FloorsEnum): Floor = map.getOrDefault(index, map.values.first())

}