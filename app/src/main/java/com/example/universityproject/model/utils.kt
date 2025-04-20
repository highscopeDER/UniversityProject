package com.example.universityproject.model

import com.example.domain.models.FloorsEnum
import com.example.universityproject.R

val FloorsEnum.resource: Int
    get() = when(this) {

        FloorsEnum.A0 -> R.drawable.a0
        FloorsEnum.A1 -> R.drawable.a1
        FloorsEnum.A2 -> R.drawable.a2
        FloorsEnum.A3 -> R.drawable.a3
        FloorsEnum.A4 -> R.drawable.a4
        FloorsEnum.A5 -> R.drawable.a5
        FloorsEnum.A6 -> R.drawable.a6

        FloorsEnum.H1 -> R.drawable.h1
        FloorsEnum.H2 -> R.drawable.h2
        FloorsEnum.H3 -> R.drawable.h3
        FloorsEnum.H4 -> R.drawable.h4
        FloorsEnum.H5 -> R.drawable.h5

        FloorsEnum.J1 -> R.drawable.j1
        FloorsEnum.J2 -> R.drawable.j2
        FloorsEnum.J3 -> R.drawable.j3
        FloorsEnum.J4 -> R.drawable.j4
    }

val FloorsEnum.bounds: Pair<Float, Float> get() = when(this) {
    FloorsEnum.A0,
    FloorsEnum.A1,
    FloorsEnum.A2,
    FloorsEnum.A3,
    FloorsEnum.A4,
    FloorsEnum.A5,
    FloorsEnum.A6 -> Pair(145.7f, 50.7f)

    FloorsEnum.H1,
    FloorsEnum.H2,
    FloorsEnum.H3,
    FloorsEnum.H4,
    FloorsEnum.H5 -> Pair(112.7533f, 29.9016f)

    FloorsEnum.J1,
    FloorsEnum.J2,
    FloorsEnum.J3,
    FloorsEnum.J4 -> Pair(61.2073f, 29.4475f)
}

val FloorsEnum.building: String
    get() = when(this) {

        FloorsEnum.A0,
        FloorsEnum.A1,
        FloorsEnum.A2,
        FloorsEnum.A3,
        FloorsEnum.A4,
        FloorsEnum.A5,
        FloorsEnum.A6 -> "Корпус 1"

        FloorsEnum.H1,
        FloorsEnum.H2,
        FloorsEnum.H3,
        FloorsEnum.H4,
        FloorsEnum.H5 -> "Корпус 8"

        FloorsEnum.J1,
        FloorsEnum.J2,
        FloorsEnum.J3,
        FloorsEnum.J4 -> "Корпус 10"
    }

val FloorsEnum.floor: String
    get() = when(this) {

        FloorsEnum.A0 -> "Подвал"

        FloorsEnum.J1,
        FloorsEnum.H1,
        FloorsEnum.A1 -> "Этаж 1"

        FloorsEnum.J2,
        FloorsEnum.H2,
        FloorsEnum.A2 -> "Этаж 2"

        FloorsEnum.J3,
        FloorsEnum.H3,
        FloorsEnum.A3 -> "Этаж 3"

        FloorsEnum.J4,
        FloorsEnum.H4,
        FloorsEnum.A4 -> "Этаж 4"

        FloorsEnum.H5,
        FloorsEnum.A5 -> "Этаж 5"


        FloorsEnum.A6 -> "Этаж 6"











    }




