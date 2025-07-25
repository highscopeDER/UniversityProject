package com.example.presentation.core.components.map.models

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope

class ClickableAreasList {

    private val list: MutableList<ClickableArea> = mutableListOf()

    fun DrawScope.draw(){
        list.forEach {
            it.draw(this)
        }
    }

    fun checkClick(p: Offset) {
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