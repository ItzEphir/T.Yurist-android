package ru.sber_tech.prod_mobile.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.mapview.MapView
import ru.sber_tech.prod_mobile.screens.addMeetScreen.AddMeetScreenViewModel
import ru.sber_tech.prod_mobile.utils.GetCoordsCallBack


@Composable
fun YandexMap(viewModel: AddMeetScreenViewModel) {
    val context = LocalContext.current

    // Adds view to Compose
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clip(RoundedCornerShape(25.dp))
            .aspectRatio(1f),
        factory = { context ->
            MapView(context).apply {


            }
        },
        update = { view ->
            val getCoords = object : GetCoordsCallBack{
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