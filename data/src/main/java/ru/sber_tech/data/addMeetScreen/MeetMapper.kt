package ru.sber_tech.data.addMeetScreen

import ru.sber_tech.data.editMeetScreen.RepresentativeDto
import ru.sber_tech.data.meets.MeetDto
import ru.sber_tech.data.meets.Person
import ru.sber_tech.data.meets.ShortMeetDto
import ru.sber_tech.domain.addMeetScreen.AddMeetModel
import ru.sber_tech.domain.editMeetScreen.EditMeetModel
import ru.sber_tech.domain.editMeetScreen.MeetModel
import ru.sber_tech.domain.editMeetScreen.PersonModel
import ru.sber_tech.domain.editMeetScreen.RepresentativeModel
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

fun EditMeetModel.toShortMeet() = ShortMeetDto(
    dateTime = "${date}T$time",
    address = address,
    latitude = latitude,
    longitude = longitude,
    operationsIds = selectedEvents.map { it.id }
)

fun MeetDto.toMeetModel(operations: List<OperationModel>) = MeetModel(
    id = id,
    date = dateTime.split("T")[0],
    time = dateTime.split("T")[1],
    address = placeAddress,
    clients = clientSidePeople.map { it.toPersonModel() },
    endDate = approximateEndDatetime.split("T")[0],
    endTime = approximateEndDatetime.split("T")[1],
    latitude = placeLatitude,
    longitude = placeLongitude,
    operations = operations
)

fun MeetDto.toEditMeetModel(operations: List<OperationModel>, representative: RepresentativeModel?) = EditMeetModel(
    date = dateTime.split("T")[0],
    time = dateTime.split("T")[1].slice(0..4),
    address = placeAddress,
    selectedEvents = operations,
    latitude = placeLatitude,
    longitude = placeLongitude,
    people = clientSidePeople.map { it.toPersonModel() },
    representative = representative
)

fun RepresentativeDto.toDomain() = RepresentativeModel(
    name = name,
    surname = surname,
    id = id,
    photoUrl = photoInnerUrl,
    position = position
)

private fun Person.toPersonModel() = PersonModel(
    name = name,
    surname = surname,
    position = position,
)