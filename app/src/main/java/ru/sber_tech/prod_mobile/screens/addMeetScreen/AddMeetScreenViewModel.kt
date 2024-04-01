package ru.sber_tech.prod_mobile.screens.addMeetScreen

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sber_tech.domain.addMeetScreen.AddMeetModel
import ru.sber_tech.domain.addMeetScreen.AddMeetState
import ru.sber_tech.domain.addMeetScreen.AddMeetState.Adding
import ru.sber_tech.domain.addMeetScreen.AddMeetState.Loading
import ru.sber_tech.domain.addMeetScreen.AddMeetUseCase
import ru.sber_tech.domain.addMeetScreen.MeetStatus.ERROR_ON_RECEIPT
import ru.sber_tech.domain.addMeetScreen.MeetStatus.SUCCESS
import ru.sber_tech.domain.getAddress.GetAddressUseCase
import ru.sber_tech.domain.getCoordinates.CoordinatesPoint
import ru.sber_tech.domain.getCoordinates.GetCoordinatesByAddressUseCase
import ru.sber_tech.prod_mobile.utils.GetCoordsCallBack
import ru.sber_tech.prod_mobile.utils.ReadCoordinatesCallBack

class AddMeetScreenViewModel(
    private val addMeetUseCase: AddMeetUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val getCoordinatesByAddressUseCase: GetCoordinatesByAddressUseCase
) : ViewModel() {

    private lateinit var getCoordinatesCallback: GetCoordsCallBack
    private lateinit var readCoordinatesCallback: ReadCoordinatesCallBack

    private val _addMeetState = MutableStateFlow<AddMeetState>(Loading)
    val addMeetState = _addMeetState.asStateFlow()

    private val _addressState = MutableStateFlow<String?>(null)
    val addressState = _addressState.asStateFlow()

    private val _location = MutableStateFlow<Location?>(null)

    fun loadElements() {
        _addMeetState.value = Adding(model = AddMeetModel(emptyList(), "", "", 0.0, 0.0))
    }

    fun addOrDeleteElement(element: String) {
        if (addMeetState.value is Adding) {
            val addingState = addMeetState.value as Adding
            if (element in addingState.model.selectedEvents) {
                _addMeetState.value = addingState.copy(
                    model = addingState.model.copy(selectedEvents = addingState.model.selectedEvents.filter { it != element })
                )
            } else {
                _addMeetState.value = addingState.copy(
                    model = addingState.model.copy(
                        selectedEvents = addingState.model.selectedEvents + listOf(element)
                    )
                )
            }
        }
    }

    fun setReadCoordsClbk(rCCallback: ReadCoordinatesCallBack) {
        readCoordinatesCallback = rCCallback
    }

    fun setCoords(getCoords: GetCoordsCallBack) {
        getCoordinatesCallback = getCoords
    }

    fun setPoint(latitude: Double, longitude: Double) {
        if (addMeetState.value is Adding) {
            val addingState = addMeetState.value as Adding
            _addMeetState.value = addingState.copy(
                model = addingState.model.copy(
                    latitude = latitude,
                    longitude = longitude
                )
            )
            viewModelScope.launch {
                _addressState.value = getAddressUseCase.invoke(latitude, longitude)
            }
        }
    }

    fun setDate(date: String) {
        if (addMeetState.value is Adding) {
            val addingState = addMeetState.value as Adding
            _addMeetState.value = addingState.copy(
                model = addingState.model.copy(
                    date = date
                )
            )
        }
    }

    fun setTime(time: String) {
        if (addMeetState.value is Adding) {
            val addingState = addMeetState.value as Adding
            _addMeetState.value = addingState.copy(
                model = addingState.model.copy(
                    time = time
                )
            )
        }
    }

    fun setCameraPosition(address: String) {
        viewModelScope.launch {
            getCoordinatesByAddressUseCase(address).also {
                Log.d("WWW", it.toString())
            }?.let {
                if (addMeetState.value is Adding) {
                    val addingState = addMeetState.value as Adding
                    _addMeetState.value =
                        addingState.copy(
                            model = addingState.model.copy(
                                latitude = it.latitude,
                                longitude = it.longitude
                            )
                        )

                    readCoordinatesCallback.read(CoordinatesPoint(it.latitude, it.longitude))
                }
            }
        }
    }

    fun setLocation(location: Location?) {
        _location.value = location
        if (addMeetState.value is Adding) {
            val addingState = addMeetState.value as Adding
            if (location != null) {
                _addMeetState.value =
                    addingState.copy(
                        model = addingState.model.copy(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )

                readCoordinatesCallback.read(
                    CoordinatesPoint(
                        location.latitude,
                        location.longitude
                    )
                )
            }
        }
    }

    fun publish(onSuccess: () -> Unit, onError: () -> Unit) {
        if (addMeetState.value is Adding) {
            val addingState = addMeetState.value as Adding
            val point = getCoordinatesCallback.getCoords()
            if (addingState.model.date == "" || addingState.model.time == "" || addingState.model.selectedEvents.isEmpty() || point == null) {
                return
            }
            _addMeetState.value = addingState.copy(
                model = addingState.model.copy(
                    latitude = point.latitude,
                    longitude = point.longitude
                )
            )
            viewModelScope.launch {
                when (addMeetUseCase.execute(addingState.model)) {
                    SUCCESS -> onSuccess()
                    ERROR_ON_RECEIPT -> onError()
                }
            }
        }
    }
}