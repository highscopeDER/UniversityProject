package com.example.universityproject.api

enum class PointTypesEnum(type: Int, desc: String) {

    DEFAULT(0, "Коридор"),
    CLASSROOM(1, "Аудитория"),
    LADDER(2, "Лестница"),
    ELEVATOR(3, "Лифт"),
    WC(4, "Туалет")

}