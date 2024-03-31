package ru.sber_tech.prod_mobile.screens.editMeetScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EditMeetScreen(string: String?) {
    if (string != null) {
        Text(text = string)
    }
}