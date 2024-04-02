package ru.sber_tech.prod_mobile.screens.addMeetScreen


import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.sber_tech.domain.addMeetScreen.AddMeetState.*
import ru.sber_tech.prod_mobile.R.drawable
import ru.sber_tech.prod_mobile.components.SegmentedButtonSelect
import ru.sber_tech.prod_mobile.components.TimePickerDialog
import ru.sber_tech.prod_mobile.components.YandexMap
import ru.sber_tech.prod_mobile.navigation.Destinations
import ru.sber_tech.prod_mobile.utils.DateUtils
import ru.sber_tech.prod_mobile.utils.GetLocation
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddMeetScreen(navController: NavController, viewModel: AddMeetScreenViewModel) {



    LaunchedEffect(key1 = Unit, block = {
        println("Refresh")
        viewModel.loadElements()
    })

    when (val uiState = viewModel.addMeetState.collectAsStateWithLifecycle().value) {
        is Adding -> Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.verticalScroll(
            rememberScrollState())) {


            val address by viewModel.addressState.collectAsStateWithLifecycle()




            val operations by viewModel.operations.collectAsStateWithLifecycle()





            Column {
                Row(Modifier.padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navController.popBackStack() }, Modifier.padding(horizontal = 8.dp)) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                    }
                    Text(text = "Новая встреча", style = MaterialTheme.typography.headlineSmall)
                }
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Text(text = "Выберите услуги", style = MaterialTheme.typography.titleLarge, modifier =  Modifier.padding(16.dp))
                    FlowRow(
                        Modifier
                            .fillMaxWidth(1f)
                            .padding(bottom = 16.dp)
                            .wrapContentHeight(align = Alignment.Top),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        operations.fastForEachIndexed { index, element ->
                            InputChip(
                                colors = InputChipDefaults.inputChipColors(
                                    selectedContainerColor = Color(
                                        android.graphics.Color.rgb(
                                            157,
                                            195,
                                            254
                                        )
                                    )
                                ),
                                modifier =
                                Modifier.padding(horizontal = 16.dp),
                                onClick = { viewModel.addOrDeleteElement(element)

                                          },
                                label = { Text(element.name) },
                                selected = element in uiState.model.selectedEvents,
                                leadingIcon = {
                                    if (element in uiState.model.selectedEvents){
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

            }

            val documents = uiState.model.selectedEvents.fold(initial = mutableListOf<String>()){ acc, operationModel ->
                acc.addAll(operationModel.documents)
                acc
            }.toSet()

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ){
                Text(text = "Адрес доставки", style = MaterialTheme.typography.titleLarge, modifier =  Modifier.padding(16.dp))
                OutlinedCard(
                    Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .fillMaxWidth()
                        .clickable {
                            viewModel.setSearchState("")
                            navController.navigate(Destinations.MapScreen.route)
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier
                                .padding(15.dp)
                                .weight(1f),
                            text = address ?: "Введите город, улицу, дом",
                        )
                    }

                }

            }










            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ){

                val date =uiState.model.date
                Text(text = if(date == "") "Выберите дату" else "Дата: ${date.replace("-", ".")}", style = MaterialTheme.typography.headlineSmall, modifier =  Modifier.padding(16.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    PickDateDialog(onConfirm = {
                        viewModel.setDate(it)
                    })
                }

            }

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ){
                val time =uiState.model.time
                Text(text = if(time == "") "Выберите время" else "Время: ${time}", style = MaterialTheme.typography.titleLarge, modifier =  Modifier.padding(16.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    PickTimeDialog(onConfirm = {
                        viewModel.setTime(it)
                    })
                }
            }

            if (documents.isNotEmpty()){
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ){
                    Text(text = "Необходимые документы", style = MaterialTheme.typography.titleLarge, modifier =  Modifier.padding(16.dp))
                    for (i in documents){
                        Text(text = i, modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp))
                    }

                }
            }
            val context = LocalContext.current
            Button(
                onClick = {
                    viewModel.publish(onSuccess = {
                        navController.popBackStack()
                    }, onError = {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    })
                },
                modifier = Modifier.padding(bottom = 30.dp, top = 30.dp),
                enabled = uiState.model.date != "" && uiState.model.time != "" && uiState.model.selectedEvents.isNotEmpty(),
            ) {
                Text(text = "Готово", color = Color.Black)
            }
        }

        is ErrorOnReceipt -> Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Ошибка при получении",
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }

        is Loading -> Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickDateDialog(onConfirm: (String) -> Unit) {
    var openDialog by remember { mutableStateOf(false) }
    Button(onClick = { openDialog = true }, modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)) {
        Text(text = "Выбрать дату", color = Color.Black)
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
fun PickTimeDialog(onConfirm: (String) -> Unit) {
    var openDialog by remember { mutableStateOf(false) }
    Button(onClick = { openDialog = true }, modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)) {
        Text(text = "Выбрать время", color = Color.Black)
    }

    if (openDialog) {
        val timePickerState = rememberTimePickerState(is24Hour = true)
        println("${timePickerState.hour}:${timePickerState.minute}")
        val pickedTime by remember {
            derivedStateOf { LocalTime.of(timePickerState.hour, timePickerState.minute) }
        }
        val formattedTime by remember {
            derivedStateOf { DateTimeFormatter.ofPattern("HH:mm").format(pickedTime) }
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



