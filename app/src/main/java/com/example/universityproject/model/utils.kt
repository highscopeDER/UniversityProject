package com.example.universityproject.model

import com.example.domain.models.FloorsEnum
import com.example.universityproject.R

val FloorsEnum.resource: Int
    get() = when(this) {
        FloorsEnum.H1 -> R.drawable.floor1
        FloorsEnum.H2 -> R.drawable.floor2
        FloorsEnum.H3 -> R.drawable.floor3
        FloorsEnum.H4 -> R.drawable.floor4
        FloorsEnum.H5 -> R.drawable.floor5
    }




