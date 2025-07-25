package com.example.presentation.screens.compose.home

import com.example.domain.models.Floor

sealed class HomeScreenState {

    data object Loading : HomeScreenState()
    data object NavigatingToRouteDetails : HomeScreenState()
    data class ShowingFloor(
        val edges: RouteEdges,
        val floor: Floor
    ) : HomeScreenState()


}