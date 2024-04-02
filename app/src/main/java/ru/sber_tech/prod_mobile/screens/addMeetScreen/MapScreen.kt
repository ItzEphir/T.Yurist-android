package ru.sber_tech.prod_mobile.screens.addMeetScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.sber_tech.prod_mobile.R
import ru.sber_tech.prod_mobile.components.YandexMap
import ru.sber_tech.prod_mobile.navigation.Destinations
import ru.sber_tech.prod_mobile.utils.GetLocation

@Composable
fun MapScreen(navController: NavController, viewModel: AddMeetScreenViewModel) {
    Column(verticalArrangement = Arrangement.Bottom) {
        GetLocation(viewModel = viewModel)
        val address by viewModel.addressState.collectAsStateWithLifecycle()

        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)
        ) {
            YandexMap.Render(viewModel, onBack = {
                navController.popBackStack()
            })
            Image(
                painter = painterResource(id = R.drawable.map_cursor),
                contentDescription = "",
                Modifier.size(20.dp)
            )
        }

        Column(modifier = Modifier.height(200.dp)) {
            OutlinedCard(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.setSearchState("")
                        navController.navigate(Destinations.SearchScreenRoute.route)
                    }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier
                            .padding(15.dp)
                            .weight(1f),
                        text = address ?: "Введите город, улицу, дом",
                    )
                }

            }
            Button(
                onClick = {
                    viewModel.setAddress()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Продолжить", color = Color.Black)
            }
        }


    }


}