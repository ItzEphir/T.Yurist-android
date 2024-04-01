package ru.sber_tech.prod_mobile.screens.searchScreen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.sber_tech.prod_mobile.screens.addMeetScreen.AddMeetScreenViewModel

@Composable
fun SearchScreen(navController: NavController, viewModel: AddMeetScreenViewModel) {
    Button(onClick = {
        viewModel.setCameraPosition("Боровское шоссе, 2к5")
        navController.popBackStack()
    }) {
        Text("Govno")
    }
}