package ru.sber_tech.prod_mobile.screens.mainScreen

import android.graphics.Color.rgb
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.sber_tech.domain.mainScreen.AllMeetsState
import ru.sber_tech.domain.mainScreen.AllMeetsState.*
import ru.sber_tech.prod_mobile.navigation.Destinations

@Composable
fun MainScreen(navController: NavController) {
    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(onClick = { navController.navigate(Destinations.AddMeetScreenRoute.route)}, containerColor = Color(rgb(254, 216, 42)), icon = {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = ""
            )
        }, text = { Text(text = "Новая встреча", color = Color.Black) })
    }) {
        val viewModel = koinViewModel<MainScreenViewModel>()

        LaunchedEffect(Unit) {
            viewModel.load()
        }

        val allMeetsState by viewModel.allMeetsState.collectAsState()
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(it)) {


            when (allMeetsState) {
                is AllMeets -> {
                    LazyColumn(Modifier.fillMaxSize()) {
                        item {
                            Row(
                                Modifier
                                    .padding(top = 16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Назначенные встречи",
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                        items((allMeetsState as AllMeets).list) { item ->
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)
                                    .clickable {
                                        navController.navigate("edit_meet/${item.id}")
                                    },
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 10.dp
                                )
                            ) {
                                Text(text = "Встреча пройдет ${item.date.replace("-", ".")} в ${item.time}", Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                                Text(text = "Адрес: ${item.address}", Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp))
                            }

                        }
                        item { 
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp))
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
}