package ru.sber_tech.prod_mobile.screens.addMeetScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.sber_tech.data.addMeetScreen.ErrorState.*
import ru.sber_tech.domain.addMeetScreen.AddMeetState.*
import ru.sber_tech.prod_mobile.R.drawable
import ru.sber_tech.prod_mobile.components.SegmentedButtonSelect
import ru.sber_tech.prod_mobile.components.TimePickerDialog
import ru.sber_tech.prod_mobile.components.YandexMap
import ru.sber_tech.prod_mobile.utils.DateUtils
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AddMeetScreen(navController: NavController) {
    val viewModel = koinViewModel<AddMeetScreenViewModel>()
    
    LaunchedEffect(key1 = Unit, block = {
        viewModel.loadElements()
    })
    
    when (val uiState = viewModel.addMeetState.collectAsState().value) {
        is Adding         -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val errorState = viewModel.errorState.collectAsState()
            Box(contentAlignment = Alignment.Center) {
                YandexMap()
                Image(
                    painter = painterResource(id = drawable.map_cursor),
                    contentDescription = "",
                    Modifier.size(20.dp)
                )
            }
            Row {
                if (errorState.value == EventError) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(20.dp)
                            .height(20.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                    ) {
                        Text(
                            text = "!",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )
                    }
                }
                SegmentedButtonSelect(
                    selectedElements = uiState.model.selectedEvents,
                    options = listOf("adasdsad", "asdasd", "sdfsdfds")
                ) {
                    viewModel.addOrDeleteElement(it)
                }
            }
            PickDateDialog(onConfirm = {
                viewModel.setDate(it)
            }, isSuccessful = errorState.value == DateError)
            PickTimeDialog(onConfirm = {
                viewModel.setTime(it)
            }, isSuccessful = errorState.value == TimeError)
            Button(
                onClick = {
                    viewModel.publish(onSuccess = {
                        navController.popBackStack()
                    })
                },
                enabled = uiState.model.date != "" && uiState.model.time != "" && uiState.model.selectedEvents.isNotEmpty(),
            ) {
                Text(text = "Готово")
            }
        }
        
        is ErrorOnReceipt -> Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Error",
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
        
        is Loading        -> Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier=Modifier.align(Alignment.Center))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickDateDialog(onConfirm: (String) -> Unit, isSuccessful: Boolean) {
    var openDialog by remember { mutableStateOf(false) }
    Row {
        if (isSuccessful) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(20.dp)
                    .height(20.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            ) {
                Text(
                    text = "!",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
        Button(onClick = { openDialog = true }) {
            Text(text = "Выбрать дату")
        }
    }
    
    if (openDialog) {
        val datePickerState = rememberDatePickerState()
        val formattedDate by remember {
            derivedStateOf {
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    .format(DateUtils.convertMillisToLocalDate(datePickerState.selectedDateMillis!!))
            }
        }
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        onConfirm(formattedDate)
                    }, enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog = false
                }) {
                    Text("Cancel")
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickTimeDialog(onConfirm: (String) -> Unit, isSuccessful: Boolean) {
    var openDialog by remember { mutableStateOf(false) }
    Row {
        if (isSuccessful) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(20.dp)
                    .height(20.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            ) {
                Text(
                    text = "!",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
        Button(onClick = { openDialog = true }) {
            Text(text = "Выбрать время")
        }
    }
    
    if (openDialog) {
        val timePickerState = rememberTimePickerState()
        val pickedTime by remember {
            derivedStateOf { LocalTime.of(timePickerState.hour, timePickerState.minute) }
        }
        val formattedTime by remember {
            derivedStateOf { DateTimeFormatter.ofPattern("hh:mm").format(pickedTime) }
        }
        
        TimePickerDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    openDialog = false
                    onConfirm(formattedTime)
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog = false
                }) { Text("Cancel") }
            },
        ) {
            TimePicker(state = timePickerState)
        }
    }
}



