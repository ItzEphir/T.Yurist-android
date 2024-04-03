package ru.sber_tech.prod_mobile.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel
import ru.sber_tech.prod_mobile.screens.addMeetScreen.AddMeetScreen
import ru.sber_tech.prod_mobile.screens.addMeetScreen.AddMeetScreenViewModel
import ru.sber_tech.prod_mobile.screens.addMeetScreen.MapScreen
import ru.sber_tech.prod_mobile.screens.editMeetScreen.EditMeetScreen
import ru.sber_tech.prod_mobile.screens.mainScreen.MainScreen
import ru.sber_tech.prod_mobile.screens.searchScreen.SearchScreen

const val MAIN_GRAPH_ROUTE = "main_nav_graph"

sealed class Destinations(
    val route: String,
) {
    data object MainScreenRoute : Destinations(route = "main")
    data object AddMeetScreenRoute : Destinations(route = "add_meet")
    data object EditScreenRoute : Destinations(route = "edit_meet/{id}")
    
    data object SearchScreenRoute : Destinations(route = "search")
    data object MapScreen : Destinations(route = "map")
    
}

@Composable
fun MainNavGraph(
    navController: NavHostController,
) {
    
    val viewModel = koinViewModel<AddMeetScreenViewModel>()
    NavHost(
        navController = navController,
        startDestination = Destinations.MainScreenRoute.route,
        route = MAIN_GRAPH_ROUTE
    ) {
        composable(route = Destinations.MainScreenRoute.route, enterTransition = {
            fadeIn(animationSpec = tween(delayMillis = 500))
        }, exitTransition = {
            fadeOut()
        }) { MainScreen(navController) }
        composable(route = Destinations.AddMeetScreenRoute.route, exitTransition = {
            fadeOut() + slideOut(animationSpec = tween(delayMillis = 100)){
                IntOffset(0, -it.height)
            }
        }) {
            AddMeetScreen(navController, viewModel)
        }
        composable(route = Destinations.EditScreenRoute.route, exitTransition = {
            fadeOut() + slideOut{
                IntOffset(-it.width, -it.height)
            }
        }) { backStackEntry ->
            EditMeetScreen(backStackEntry.arguments?.getString("id")!!, navController)
        }
        composable(route = Destinations.SearchScreenRoute.route) {
            SearchScreen(navController, viewModel)
        }
        composable(route = Destinations.MapScreen.route, exitTransition = {
            fadeOut()
        }) {
            MapScreen(navController = navController, viewModel = viewModel)
        }
    }
}