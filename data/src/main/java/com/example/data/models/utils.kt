package com.example.data.models

import com.example.domain.models.FloorsEnum

fun Int.toFloorsEnum(): FloorsEnum = when(this) {
    1 -> FloorsEnum.H1
    2 -> FloorsEnum.H2
    3 -> FloorsEnum.H3
    4 -> FloorsEnum.H4
    5 -> FloorsEnum.H5
    else -> throw Exception("Int.toFloorsEnum call: Illegal Number. No FloorsEnum associated with this number!")
}

fun String.isLadder(): Boolean = this.last() == 'l'

val String.floor: Int
    get() = this[1].digitToInt()

val String.building: Int
    get() = when(this[0]) {
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