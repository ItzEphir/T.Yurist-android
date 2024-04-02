package ru.sber_tech.prod_mobile.screens.editMeetScreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import ru.sber_tech.domain.editMeetScreen.EditMeetModel

@Composable
fun YandexMap2(editMeetModel: EditMeetModel, onBack: () -> Unit) {
    
    var visible by remember {
        mutableStateOf(true)
    }
    
    BackHandler {
        visible = false
        onBack()
    }
    
    
    AnimatedVisibility(visible = visible) {
        AndroidView(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clip(RoundedCornerShape(25.dp))
            .aspectRatio(1f), factory = { context ->
            MapView(context).apply {
                mapWindow.map.isScrollGesturesEnabled = false
                mapWindow.map.isRotateGesturesEnabled = false
                mapWindow.map.isTiltGesturesEnabled = false
                mapWindow.map.isZoomGesturesEnabled = false
            }
        }, update = { view ->
            view.mapWindow.map.move(
                CameraPosition(
                    Point(
                        editMeetModel.latitude, editMeetModel.longitude
                    ), 15.0f, 0.0f, 0.0f
                ), Animation(Animation.Type.SMOOTH, 1F), null
            )
        })
        
    }
}