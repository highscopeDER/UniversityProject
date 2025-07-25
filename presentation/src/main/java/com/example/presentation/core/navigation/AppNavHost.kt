package com.example.presentation.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.screens.compose.home.HomeScreen
import com.example.presentation.screens.compose.routeDetails.RouteDetailsScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = HomeScreenDestination
    ) {

        composable<HomeScreenDestination> {
            HomeScreen(
                navigateToRouteDetails = {
                    navController.navigate(route = RouteDetailsScreenDestination)
                }
            )
        }

        composable<RouteDetailsScreenDestination> {
            RouteDetailsScreen(
                navigateToHome = { navController.popBackStack() }
            )
        }

    }

}

@Serializable
object HomeScreenDestination

@Serializable
object RouteDetailsScreenDestination

