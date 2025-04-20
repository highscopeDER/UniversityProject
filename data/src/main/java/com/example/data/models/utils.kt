package com.example.data.models

import com.example.domain.models.FloorsEnum
import com.example.domain.models.PointPosition

fun PointPosition.toFloorsEnum(): FloorsEnum  = when(this.name.first()) {
    'a' -> when(this.name[1].digitToInt()) {
        0 -> FloorsEnum.A0
        1 -> FloorsEnum.A1
        2 -> FloorsEnum.A2
        3 -> FloorsEnum.A3
        4 -> FloorsEnum.A4
        5 -> FloorsEnum.A5
        6 -> FloorsEnum.A6
        else -> FloorsEnum.A0
    }

    'h' -> when(this.name[1].digitToInt()) {
        1 -> FloorsEnum.H1
        2 -> FloorsEnum.H2
        3 -> FloorsEnum.H3
        4 -> FloorsEnum.H4
        5 -> FloorsEnum.H5
        else -> FloorsEnum.H1
    }

    'j' -> when(this.name[1].digitToInt()) {
        1 -> FloorsEnum.J1
        2 -> FloorsEnum.J2
        3 -> FloorsEnum.J3
        4 -> FloorsEnum.J4
        else -> FloorsEnum.J1
    }

    else -> FloorsEnum.A0
}

fun PointPosition.isLadder(): Boolean = this.type == PointType.STAIR
fun PointPosition.isTransition(): Boolean = this.type == PointType.TRANSITION

val PointPosition.type: PointType get() = when(this.name.last()) {
    's' -> PointType.STAIR
    't' -> PointType.TRANSITION
    'e' -> PointType.ENTRANCE
    'l' -> PointType.ELEVATOR
    'm' -> PointType.WCM
    'w' -> PointType.WCW
    'c' -> PointType.WC
    else -> PointType.NO_TYPE
}

enum class PointType {
    STAIR, TRANSITION, ENTRANCE, WCM, WCW, WC, ELEVATOR, NO_TYPE
}

val PointPosition.floor: Int
    get() = this.name[1].digitToInt()

val PointPosition.building: Int
    get() = when(this.name[0]) {
        'a' -> 1
        'b' -> 2
        'c' -> 3
        'd' -> 4
        'e' -> 5
        'f' -> 6
        'g' -> 7
        'h' -> 8
        'i' -> 9
        'j' -> 10
        'k' -> 11
        'l' -> 12
        'm' -> 13
        else -> 0
    }