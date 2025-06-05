package com.example.presentation.screens.viewModels

import androidx.lifecycle.ViewModel
import com.example.domain.models.Route
import com.example.domain.models.RoutePart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RouteViewerViewModel : ViewModel() {

    private val route = MutableStateFlow<Route>(Route(emptyList()))

    private val _currentPart = MutableStateFlow<StepState>(StepState.Empty)
    val currentPart = _currentPart.asStateFlow()

    private val _partsCount = MutableStateFlow<Int>(0)
    val partsCount = _partsCount.asStateFlow()

    fun setRoute(r: Route) {
        route.value = r
        _currentPart.value = StepState.Showing(r.routeParts.first())
        _partsCount.value = r.routeParts.count()
    }

    fun nextPart(){
        if (currentPart.value is StepState.Showing)  {
            val curPart = (currentPart.value as StepState.Showing).part
            if(curPart != route.value.routeParts.last()) {
                _currentPart.value = StepState.Showing(
                    route.value.routeParts[
                        route.value.routeParts.indexOf(curPart) + 1
                    ]
                )
            }
        }
    }

    fun previousPart(){
        if (currentPart.value is StepState.Showing)  {
            val curPart = (currentPart.value as StepState.Showing).part
            if(curPart != route.value.routeParts.first()) {
                _currentPart.value = StepState.Showing(
                    route.value.routeParts[
                        route.value.routeParts.indexOf(curPart) - 1
                    ]
                )
            }
        }
    }

    fun part(position: Int){
        _currentPart.value = StepState.Showing(route.value.routeParts[position])
    }

    fun getCurrentPartIndex(p: RoutePart): Int {
//        return if (currentPart.value is StepState.Showing) {
//            route.value.routeParts.indexOf((currentPart.value as StepState.Showing).s)
//        } else {
//            0
//        }

        return route.value.routeParts.indexOf(p)
    }


    sealed class StepState(){
        data object Empty: StepState()
        data object Loading: StepState()
        class Showing(val part: RoutePart): StepState() {}
    }

}