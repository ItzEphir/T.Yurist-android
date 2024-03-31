package ru.sber_tech.prod_mobile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButtonSelect(selectedElements: List<String>, options: List<String>, onClick: (String) -> Unit) {
    Row {
        options.forEachIndexed { index, label ->
            InputChip(
                modifier = if(index == 0){
                    Modifier.padding(start = 29.dp, end = 4.dp)}else{
                    Modifier.padding(horizontal = 4.dp)},
                onClick = { onClick(label) },
                label = { Text(label) },
                selected = label in selectedElements,
                leadingIcon = {
                    if (label in selectedElements){
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }

                }
            )
        }
    }


}