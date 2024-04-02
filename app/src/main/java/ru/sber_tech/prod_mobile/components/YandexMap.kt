package ru.sber_tech.prod_mobile.components

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import ru.sber_tech.domain.getCoordinates.CoordinatesPoint
import ru.sber_tech.prod_mobile.screens.addMeetScreen.AddMeetScreenViewModel
import ru.sber_tech.prod_mobile.utils.GetCoordsCallBack
import ru.sber_tech.prod_mobile.utils.ReadCoordinatesCallBack


object YandexMap {

    lateinit var view: MapView

    lateinit var viewModel: AddMeetScreenViewModel

    private val cameraListener: CameraListener = object : CameraListener {
        override fun onCameraPositionChanged(
            map: Map,
            cameraPosition: CameraPosition,
            cameraUpdateReason: CameraUpdateReason,
            b: Boolean
        ) {
            if (b) {
                Log.d("CAMERA", "q")
                val centerX = view.mapWindow.width() / 2f
                val centerY = view.mapWindow.height() / 2f
                val centerPoint = ScreenPoint(centerX, centerY)
                val worldPoint = view.mapWindow.screenToWorld(centerPoint)!!
                viewModel.setPoint(worldPoint.latitude, worldPoint.longitude)
            }
        }

    }

    @Composable
    fun Render(viewModel: AddMeetScreenViewModel, onBack: () -> Unit) {
        val context = LocalContext.current

        this.viewModel = viewModel

        var visible by remember {
            mutableStateOf(true)
        }

        BackHandler {
            visible = false
            onBack()
        }

        AnimatedVisibility(visible = visible) {
            // Adds view to Compose
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    ,
                factory = { context ->
                    MapView(context).apply {
                        view = this
                        getMap().addCameraListener(cameraListener)


                        val readCoords = object : ReadCoordinatesCallBack {
                            override fun read(coordinatesPoint: CoordinatesPoint) {
                                view.getMap().move(
                                    CameraPosition(
                                        Point(
                                            coordinatesPoint.latitude,
                                            coordinatesPoint.longitude
                                        ), 15.0f, 0.0f, 0.0f
                                    ),
                                    Animation(Animation.Type.SMOOTH, 1F),
                                    null
                                )
                            }

                        }
                        viewModel.setReadCoordsClbk(readCoords)
                        viewModel.setDefaultCameraPosition()
                    }
                },
                update = { view ->
                    val getCoords = object : GetCoordsCallBack {
                        override fun getCoords(): Point? {
                            val centerX = view.mapWindow.width() / 2f
                            val centerY = view.mapWindow.height() / 2f
                            val centerPoint = ScreenPoint(centerX, centerY)
                            val worldPoint = view.mapWindow.screenToWorld(centerPoint)
                            return worldPoint
                        }
                    }
                    viewModel.setCoords(getCoords)
                }
            )
        }
    }
}