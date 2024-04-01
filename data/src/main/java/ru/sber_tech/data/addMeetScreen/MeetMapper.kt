package ru.sber_tech.data.addMeetScreen

import ru.sber_tech.data.meets.MeetDto
import ru.sber_tech.data.meets.ShortMeetDto
import ru.sber_tech.domain.addMeetScreen.AddMeetModel
import ru.sber_tech.domain.mainScreen.LiteMeetModel
import ru.sber_tech.domain.operations.OperationModel

fun AddMeetModel.toDto(): ShortMeetDto = ShortMeetDto(
    dateTime = "${date}T$time",
    address = address,
    latitude = latitude,
    longitude = longitude,
    operationsIds = selectedEvents.map { it.id }
)

fun MeetDto.toLiteModel(operations: List<OperationModel>) = LiteMeetModel(
    id = id,
    operations = operations,
    address = placeAddress,
    date = dateTime.split("T")[0],
    time = dateTime.split("T")[1],
    endTime = approximateEndDatetime,
    peoples = clientSidePeople.map { "${it.name} ${it.surname} - ${it.position}" }
)