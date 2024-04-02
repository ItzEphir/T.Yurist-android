package ru.sber_tech.prod_mobile.screens.searchScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchCard(address: String, onClick: (address: String) -> Unit) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
            onClick(address)
        }, shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(vertical = 8.dp), contentAlignment = Alignment.CenterStart) {
            Text(text = address, modifier = Modifier.padding(8.dp))
        }
    }

}