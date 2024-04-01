package ru.sber_tech.data.getCoordinates

import ru.sber_tech.domain.getCoordinates.CoordinatesPoint

fun Point.convert(): CoordinatesPoint{
    val strs = pos.split(" ")
    return CoordinatesPoint(latitude = strs[1].toDouble(), longitude = strs[0].toDouble())
}