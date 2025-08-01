package com.example.presentation.screens.compose.home

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.example.domain.models.Floor
import com.example.domain.models.FloorsEnum
import com.example.domain.models.RoutePoint
import com.example.domain.models.alias.Floors
import com.example.domain.usecases.LoadUseCase
import com.example.domain.usecases.RequestRouteUseCase
import com.example.presentation.model.building
import com.example.presentation.model.floor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: need to remake this viewmodel for home screen

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val loadUseCase: LoadUseCase,
    private val requestRouteUseCase: RequestRouteUseCase
) : ViewModel() {


    private val _uiState: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    private val classrooms = MutableStateFlow<List<RoutePoint>>(emptyList())

    private val floors = MutableStateFlow(Floors(emptyMap()))


    fun loadFloors(){
        viewModelScope.launch {

            _uiState.value = HomeScreenState.Loading
            loadUseCase.execute().collectLatest {
                floors.value = it.floors
                classrooms.value = it.classrooms
                _uiState.value = HomeScreenState.ShowingFloor(
                    edges = RouteEdges(null, null),
                    floor = it.floors.map.entries.first().value
                )
            }
        }
    }

    fun setRouteStart(routePoint: RoutePoint) {
        if (uiState.value is HomeScreenState.ShowingFloor) {
            with(uiState.value as HomeScreenState.ShowingFloor) {
                _uiState.value = copy(
                    edges = edges.copy(
                        start = routePoint
                    )
                )
            }
        }
    }

    fun setRouteEnd(routePoint: RoutePoint) {
        if (uiState.value is HomeScreenState.ShowingFloor) {
            with(uiState.value as HomeScreenState.ShowingFloor) {
                _uiState.value = copy(
                    edges = edges.copy(
                        end = routePoint
                    )
                )
            }
        }
    }




}