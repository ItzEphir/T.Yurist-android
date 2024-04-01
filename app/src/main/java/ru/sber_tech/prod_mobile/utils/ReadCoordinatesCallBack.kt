package ru.sber_tech.prod_mobile.utils

import ru.sber_tech.domain.getCoordinates.CoordinatesPoint

interface ReadCoordinatesCallBack {

    fun read(coordinatesPoint: CoordinatesPoint)
}