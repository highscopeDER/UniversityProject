package com.example.presentation.screens.compose.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.models.Floor
import com.example.domain.models.FloorsEnum
import com.example.presentation.R
import com.example.presentation.core.components.map.VectorComposeMap
import com.example.presentation.core.components.map.models.ClickableAreasList
import com.example.presentation.core.components.routeEdgesPicker.RouteEdgesPicker
import com.example.presentation.core.theme.UniversityProjectTheme
import com.example.presentation.model.resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>(),
    navigateToRouteDetails: () -> Unit
) {

    LaunchedEffect("l1") {
        viewModel.loadFloors()
    }

    when(
        val state = viewModel.uiState.collectAsState().value
    ){
        HomeScreenState.Loading -> {}

        HomeScreenState.NavigatingToRouteDetails -> navigateToRouteDetails()

        is HomeScreenState.ShowingFloor -> {
            Box(Modifier.fillMaxSize()) {
                VectorComposeMap(
                    modifier = Modifier.fillMaxSize(),
                    clickableAreasList = ClickableAreasList(),
                    img = ImageVector.vectorResource(
                        state.floor.enum.resource
                    )
                )

                RouteEdgesPicker(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15f)
                        .align(Alignment.BottomCenter)
                )

            }
        }
    }

}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    navigateToRouteDetails: () -> Unit
) {

    when(state){
        HomeScreenState.Loading -> {}
        HomeScreenState.NavigatingToRouteDetails -> navigateToRouteDetails()
        is HomeScreenState.ShowingFloor -> {
            Box(Modifier.fillMaxSize()) {
                VectorComposeMap(
                    modifier = Modifier.fillMaxSize(),
                    clickableAreasList = ClickableAreasList(),
                    img = ImageVector.vectorResource(
                        state.floor.enum.resource
                    )
                )

                RouteEdgesPicker(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15f)
                        .align(Alignment.BottomCenter)
                )

            }
        }
    }

}

@Preview
@Composable
fun HomeScreenPreview(){

    val state = HomeScreenState.ShowingFloor(
        RouteEdges(null, null),
        Floor(FloorsEnum.A0, emptyList())
    )

    UniversityProjectTheme {
        HomeScreenContent(
            Modifier.fillMaxSize(),
            state
        ) {}
    }
}