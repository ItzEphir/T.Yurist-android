package ru.sber_tech.prod_mobile.screens.mainScreen

import android.graphics.Color.rgb
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.sber_tech.domain.mainScreen.AllMeetsState.*
import ru.sber_tech.prod_mobile.navigation.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(onClick = { navController.navigate(Destinations.AddMeetScreenRoute.route) },
            containerColor = Color(rgb(254, 216, 42)),
            icon = {

            },
            text = { Text(text = "Новая встреча", color = Color.Black) })
    }) {
        val viewModel = koinViewModel<MainScreenViewModel>()
        
        LaunchedEffect(Unit) {
            viewModel.load()
        }
        
        val allMeetsState by viewModel.allMeetsState.collectAsState()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(it)
        ) {
            when (allMeetsState) {
                is AllMeets -> {
                    val pullToRefreshState = rememberPullToRefreshState()
                    
                    val scope = rememberCoroutineScope()
                    
                    LaunchedEffect(key1 = pullToRefreshState.isRefreshing, block = {
                        if (pullToRefreshState.isRefreshing) {
                            viewModel.refresh(onEnd = {
                                pullToRefreshState.endRefresh()
                            })
                        }
                    })
                    
                    Box(modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
                        LazyColumn(Modifier.fillMaxSize()) {
                            if (!pullToRefreshState.isRefreshing) {
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
                                        Text(
                                            text = "Встреча пройдет ${
                                                item.date.replace(
                                                    "-", "."
                                                )
                                            } в ${item.time.slice(0..4)}",
                                            Modifier.padding(16.dp),
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = "Адрес: ${item.address}", Modifier.padding(
                                                start = 16.dp, end = 16.dp, bottom = 16.dp
                                            )
                                        )
                                    }
                                    
                                }
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(80.dp)
                                    )
                                }
                            }
                        }
                        
                        PullToRefreshContainer(
                            state = pullToRefreshState, modifier = Modifier.align(
                                Alignment.TopCenter
                            )
                        )
                    }
                }
                
                ErrorOnReceipt -> {
                    Column(
                        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Ошибка при получении")
                        Button(onClick = {
                            viewModel.reload()
                        }){
                            Text("Повторить")
                        }
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