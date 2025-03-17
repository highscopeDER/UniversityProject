package com.example.universityproject.screens.map.clickable

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF

class ClickableAreasList(context: Context) {

    private val list: MutableList<ClickableArea> = mutableListOf()

    fun draw(canvas: Canvas){
        list.forEach {
            it.dispatchDrawing(canvas)
        }
    }

    fun checkClick(p: PointF) {
        list.forEach {
            it.checkIfClicked(p)
        }
    }

    fun loadListOfAreas(newList: List<ClickableArea>) {
        list.clear()
        newList.forEach {
            list.add(it)
        }
    }

    fun addArea(area: ClickableArea) {
        list.add(area)

    }

    fun unselectAll(){
        list.forEach {
            it.unselect()
        }
    }



}