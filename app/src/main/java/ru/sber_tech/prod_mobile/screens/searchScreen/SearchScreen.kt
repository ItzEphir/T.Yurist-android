package ru.sber_tech.prod_mobile.screens.searchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.sber_tech.prod_mobile.screens.addMeetScreen.AddMeetScreenViewModel

@Composable
fun SearchScreen(navController: NavController, viewModel: AddMeetScreenViewModel) {
    Column {
        val searchState by viewModel.searchState.collectAsStateWithLifecycle()
        OutlinedTextField(value = searchState, onValueChange = {
            viewModel.setSearchState(it)
        }, modifier = Modifier.fillMaxWidth().padding(15.dp))
        val addresses by viewModel.addresses.collectAsStateWithLifecycle()
        LazyColumn(Modifier.fillMaxSize()) {
            items(addresses){item ->
                SearchCard(address = item, onClick = {
                    viewModel.setCameraPosition(it)
                    navController.popBackStack()
                })
            }
        }
    }
}