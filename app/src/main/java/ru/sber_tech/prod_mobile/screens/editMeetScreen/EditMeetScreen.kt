package ru.sber_tech.prod_mobile.screens.editMeetScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditMeetScreen(id: String) {
    val viewModel = koinViewModel<EditMeetScreenViewModel>()
    LaunchedEffect(key1 = Unit, block = {
        viewModel.load()
    })
}