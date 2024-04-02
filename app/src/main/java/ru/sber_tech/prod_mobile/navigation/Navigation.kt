package ru.sber_tech.prod_mobile.navigation

import androidx.compose.runtime.Composable
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
    val route: String
) {
    data object MainScreenRoute : Destinations(route = "main")
    data object AddMeetScreenRoute : Destinations(route = "add_meet")
    data object EditScreenRoute : Destinations(route = "edit_meet/{id}")

    data object SearchScreenRoute : Destinations(route = "search")
    data object MapScreen : Destinations(route = "map")

}

@Composable
fun MainNavGraph(
    navController: NavHostController
) {

    val viewModel = koinViewModel<AddMeetScreenViewModel>()
    NavHost(
        navController = navController,
        startDestination = Destinations.MainScreenRoute.route,
        route = MAIN_GRAPH_ROUTE
    ) {
        composable(route = Destinations.MainScreenRoute.route) { MainScreen(navController) }
        composable(route = Destinations.AddMeetScreenRoute.route) {
            AddMeetScreen(navController, viewModel)
        }
        composable(route = Destinations.EditScreenRoute.route) { backStackEntry ->
            EditMeetScreen(backStackEntry.arguments?.getString("id")!!)
        }
        composable(route = Destinations.SearchScreenRoute.route) {
            SearchScreen(navController, viewModel)
        }
        composable(route = Destinations.MapScreen.route){
            MapScreen(navController = navController, viewModel = viewModel)
        }
    }
}