package com.example.universityproject.screens.viewModels

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.domain.models.Floor
import com.example.domain.models.FloorsEnum
import com.example.domain.models.Route
import com.example.domain.models.alias.Floors
import com.example.domain.models.RoutePoint
import com.example.domain.repositories.ApiRepository
import com.example.domain.usecases.LoadUseCase
import com.example.domain.usecases.RequestRouteUseCase
import com.example.universityproject.model.building
import com.example.universityproject.model.floor
import com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment.MainBottomSheetFragment
import com.example.universityproject.screens.map.PathEdgesSetter
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

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val loadUseCase: LoadUseCase,
    private val requestRouteUseCase: RequestRouteUseCase
) : ViewModel() {

    private val _navigateToRouteViewer = MutableSharedFlow<Route>()
    val navigateToRouteViewer = _navigateToRouteViewer.asSharedFlow()

    private val _startPoint: MutableStateFlow<RoutePoint?> = MutableStateFlow(null)
    private val _endPoint: MutableStateFlow<RoutePoint?> = MutableStateFlow(null)

    val startPoint = _startPoint.asStateFlow()
    val endPoint = _endPoint.asStateFlow()

    private val _floors = MutableStateFlow(Floors(emptyMap()))
    private val floors: StateFlow<Floors> = _floors.asStateFlow()

    private val _classrooms = MutableStateFlow<List<RoutePoint>>(emptyList())
    private val classrooms: StateFlow<List<RoutePoint>> = _classrooms.asStateFlow()


    init {

        viewModelScope.launch {
            combine(startPoint, endPoint) { s, e ->
               s != null && e != null
            }.collectLatest {
                if (it) navigateToRoute()
            }
        }

    }

    private val _currentFloor: MutableStateFlow<FloorState> = MutableStateFlow(FloorState.Empty)
    private val _currentFloorText: MutableStateFlow<String> = MutableStateFlow("Этаж 1")
    private val _currentBuildingText: MutableStateFlow<String> = MutableStateFlow("Корпус 1")

    val currentFloor = _currentFloor.asStateFlow()
    val currentFloorText = _currentFloorText.asStateFlow()
    val currentBuildingText = _currentBuildingText.asStateFlow()

    private val _popUpMenu = MutableSharedFlow<String>()
    val popUpMenu = _popUpMenu.asSharedFlow()

    val pathEdgesSetter = PathEdgesSetter(
        {_startPoint.value = it},
        {_endPoint.value = it}
    )

    fun loadFloors(){
        viewModelScope.launch {
            _currentFloor.value = FloorState.Loading
            loadUseCase.execute().collectLatest {
                _floors.value = it.floors
                _classrooms.value = it.classrooms
                setFloor(it.floors.map.keys.first())
            }
        }
    }

    fun showPopUpMenu(){
        viewModelScope.launch {
            _popUpMenu.emit("")
        }
    }

    fun setFloor(num: FloorsEnum) {
        _currentFloor.value = FloorState.Showing(floors.value.getFloor(num))
        _currentFloorText.value = num.floor
        _currentBuildingText.value = num.building
    }

    fun showBottomSheet(fm: FragmentManager, start: Boolean){
        MainBottomSheetFragment(start, classrooms.value).show(fm, null)
    }

    fun setStartPoint(p: RoutePoint) {
        _startPoint.value = p
    }

    fun setEndPoint(p: RoutePoint) {
        _endPoint.value = p
    }

    private fun navigateToRoute(){
        val route = requestRouteUseCase.execute(startPoint.value!!, endPoint.value!!)
        viewModelScope.launch {
            _navigateToRouteViewer.emit(route)
        }
        _startPoint.value = null
        _endPoint.value = null
    }

    sealed class FloorState {
        data object Empty : FloorState()
        data object Loading: FloorState()
        data class Error(val msg: String) : FloorState()
        data class Showing(val floor: Floor): FloorState()
    }

}