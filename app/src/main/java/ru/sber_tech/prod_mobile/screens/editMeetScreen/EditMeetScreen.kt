package ru.sber_tech.prod_mobile.screens.editMeetScreen

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.koinViewModel
import ru.sber_tech.domain.editMeetScreen.EditMeetState.*
import ru.sber_tech.prod_mobile.R.drawable
import ru.sber_tech.prod_mobile.screens.addMeetScreen.PickDateDialog
import ru.sber_tech.prod_mobile.screens.addMeetScreen.PickTimeDialog

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditMeetScreen(id: String, navController: NavController) {
    val viewModel = koinViewModel<EditMeetScreenViewModel>()
    LaunchedEffect(key1 = Unit, block = {
        viewModel.load(id)
    })
    val uiState by viewModel.editMeetState.collectAsStateWithLifecycle()
    when (uiState) {
        is Editing -> {
            val operations by viewModel.operations.collectAsStateWithLifecycle()
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                
                
                Row(
                    Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        Modifier.padding(horizontal = 8.dp)
                    ) {
                        Icon(imageVector = Filled.ArrowBack, contentDescription = "")
                    }
                    Text(text = "Детали", style = MaterialTheme.typography.headlineSmall)
                }
                
                
                Box {
                    YandexMap2(editMeetModel = (uiState as Editing).model, onBack = {
                        navController.popBackStack()
                    })
                    
                    Image(
                        painter = painterResource(id = drawable.map_cursor),
                        contentDescription = null,
                        modifier = Modifier
                            .align(
                                Alignment.Center
                            )
                            .size(20.dp)
                    )
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
                    Text(
                        text = "Адрес доставки",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                    Card(
                        Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                modifier = Modifier
                                    .padding(15.dp)
                                    .weight(1f),
                                text = (uiState as Editing).model.address,
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
                ) {
                    Text(
                        text = "Выберите услуги",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                    FlowRow(
                        Modifier
                            .fillMaxWidth(1f)
                            .padding(bottom = 16.dp)
                            .wrapContentHeight(align = Alignment.Top),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        operations.fastForEachIndexed { index, element ->
                            InputChip(colors = InputChipDefaults.inputChipColors(
                                selectedContainerColor = Color(
                                    android.graphics.Color.rgb(
                                        254, 216, 42
                                    )
                                )
                            ),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                onClick = { viewModel.addOrDeleteElement(element) },
                                label = { Text(element.name) },
                                selected = element in (uiState as Editing).model.selectedEvents,
                                leadingIcon = {
                                    if (element in (uiState as Editing).model.selectedEvents) {
                                        Icon(
                                            imageVector = Icons.Filled.Done,
                                            contentDescription = "Localized Description",
                                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                                        )
                                    }
                                    
                                })
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
                ) {
                    
                    val date = (uiState as Editing).model.date
                    Text(
                        text = if (date == "") "Выберите дату" else "Дата: ${
                            date.replace(
                                "-", "."
                            )
                        }",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
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
                ) {
                    val time = (uiState as Editing).model.time
                    Text(
                        text = if (time == "") "Выберите время" else "Время: $time",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        PickTimeDialog(onConfirm = {
                            viewModel.setTime(it)
                        })
                    }
                }
                
                val documents =
                    (uiState as Editing).model.selectedEvents.fold(initial = mutableListOf<String>()) { acc, operationModel ->
                        acc.addAll(operationModel.documents)
                        acc
                    }.toSet()
                
                AnimatedVisibility(visible = documents.isNotEmpty(), enter = fadeIn() + slideIn {
                    IntOffset(0, -2 * it.height)
                }, exit = fadeOut() + slideOut {
                    IntOffset(0, -2 * it.height)
                }) {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        Text(
                            text = "Необходимые документы",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                        documents.forEach {
                            Text(
                                text = it, modifier = Modifier.padding(
                                    bottom = 16.dp, start = 16.dp, end = 16.dp
                                )
                            )
                        }
                        
                    }
                }
                
                val representative = (uiState as Editing).model.representative
                if (representative != null) {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {

                        Text(
                            text = "Представитель организации",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = rememberAsyncImagePainter(representative.photoUrl),
                                contentDescription = "null",
                                modifier = Modifier
                                    .size(75.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                            Text(
                                text = "${representative.surname} ${representative.name}, ${representative.position}",
                                modifier = Modifier.padding(
                                     start = 16.dp, end = 16.dp
                                )
                            )
                        }

                        
                    }
                }
                
                val people = (uiState as Editing).model.people
                
                
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Text(
                        text = "Представители клиента",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                    people.forEach {
                        Text(
                            text = "${it.surname} ${it.name}, ${it.position}",
                            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                        )
                    }
                    
                }
                
                
                
                
                Box(modifier = Modifier.fillMaxWidth()) {
                    
                    val context = LocalContext.current
                    Button(
                        onClick = {
                            viewModel.publish(id, onSuccess = {
                                navController.popBackStack()
                            }, onError = {
                                Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG).show()
                            })
                        },
                        modifier = Modifier
                            .padding(vertical = 32.dp, horizontal = 16.dp)
                            .align(Alignment.CenterEnd),
                        enabled = (uiState as Editing).model.date != "" && (uiState as Editing).model.time != "" && (uiState as Editing).model.selectedEvents.isNotEmpty(),
                    ) {
                        Text(text = "Готово", color = Color.Black)
                    }
                    
                    Button(
                        onClick = {
                            viewModel.delete(id, onSuccess = {
                                navController.popBackStack()
                            }, onError = {
                                Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG).show()
                            })
                        },
                        modifier = Modifier
                            .padding(vertical = 32.dp, horizontal = 16.dp)
                            .align(Alignment.CenterStart),
                        enabled = (uiState as Editing).model.date != "" && (uiState as Editing).model.time != "" && (uiState as Editing).model.selectedEvents.isNotEmpty(),
                    ) {
                        Text(text = "Удалить", color = Color.Black)
                    }
                }
            }
        }
        
        is ErrorOnReceipt -> Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Ошибка при получении")
            Button(onClick = {
                viewModel.reload(id)
            }) {
                Text("Повторить")
            }
        }
        
        is Loading -> Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Companion.Center))
        }
    }
    
}