package com.example.presentation.screens.compose.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presentation.R
import com.example.presentation.core.components.map.VectorComposeMap
import com.example.presentation.core.components.map.models.ClickableAreasList
import com.example.presentation.core.theme.UniversityProjectTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToRouteDetails: () -> Unit
) {

    val viewModel: HomeScreenViewModel = viewModel()

    val state by viewModel.uiState.collectAsState()

    when(state){
        HomeScreenState.Loading -> TODO()
        HomeScreenState.NavigatingToRouteDetails -> TODO()
        is HomeScreenState.ShowingFloor -> {

        }
    }

    Box(Modifier.fillMaxSize()) {
        VectorComposeMap(
            modifier = Modifier.fillMaxSize(),
            clickableAreasList = ClickableAreasList(),
            img = ImageVector.vectorResource(R.drawable.a0)
        )
    }

}

@Preview
@Composable
fun HomeScreenPreview(){
    UniversityProjectTheme {
        HomeScreen(Modifier.fillMaxSize()) {}
    }
}