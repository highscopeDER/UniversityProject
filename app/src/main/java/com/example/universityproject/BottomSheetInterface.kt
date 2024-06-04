package com.example.universityproject

interface BottomSheetInterface {

    var startPoint: String?
    var endPoint: String?

    fun updateUI()

    object selectionType {
        const val START = 0
        const val  END = 1
    }

}