package ru.sber_tech.prod_mobile.screens.mainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.sber_tech.domain.mainScreen.AllMeetsState
import ru.sber_tech.domain.mainScreen.AllMeetsState.*
import ru.sber_tech.prod_mobile.navigation.Destinations

@Composable
fun MainScreen(navController: NavController){
    val viewModel = koinViewModel<MainScreenViewModel>()

    val allMeetsState by viewModel.allMeetsState.collectAsState()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { navController.navigate(Destinations.AddMeetScreenRoute.route) }) {
            Text(text = "Новая встреча")
        }
        when(allMeetsState){
            is AllMeets -> {
                LazyColumn {
                    items((allMeetsState as AllMeets).list){ item ->
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(15.dp)
                            .clickable {
                                navController.navigate("edit_meet/${item.id}")
                            }) {
                            Text(text = item.id)
                        }

                    }
                }
            }
            ErrorOnReceipt -> {
                Box(
                    Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(text = "Ошибка при получении")
                }
            }
            Loading -> {
                Box(
                    Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                }
            }
        }
    }
}