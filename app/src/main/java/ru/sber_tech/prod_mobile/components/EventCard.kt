package ru.sber_tech.prod_mobile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sber_tech.domain.mainScreen.LiteMeetModel
import ru.sber_tech.domain.operations.OperationModel
import ru.sber_tech.prod_mobile.ui.theme.Prod_mobileTheme

@Composable
fun EventCard(meetModel: LiteMeetModel, modifier: Modifier = Modifier) {
    
    Card(
        shape = RoundedCornerShape(12.dp), modifier = modifier.padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text("Название")
            LazyRow(modifier = Modifier.width(Max)) {
            
            }
        }
    }
}