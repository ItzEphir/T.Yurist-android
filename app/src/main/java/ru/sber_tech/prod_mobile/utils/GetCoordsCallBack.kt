package ru.sber_tech.prod_mobile.utils

import com.yandex.mapkit.geometry.Point

interface GetCoordsCallBack {

    fun getCoords(): Point?
}