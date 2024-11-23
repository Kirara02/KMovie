package id.kirara.kmovie.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.rememberCameraPositionState
import id.kirara.kmovie.core.CurrentLocationMarker
import id.kirara.kmovie.core.GoogleMapsComponent
import id.kirara.kmovie.core.MapsMarker
import id.kirara.kmovie.domain.location.DeviceLocation
import id.kirara.kmovie.ui.scene.map.Cinema
import id.kirara.kmovie.ui.scene.map.MapUiState
import kmovie.composeapp.generated.resources.Res
import kmovie.composeapp.generated.resources.ic_maps_marker
import kmovie.composeapp.generated.resources.ic_maps_marker_user
import kotlinx.coroutines.launch

@Composable
actual fun Map(
    modifier: Modifier,
    uiState: MapUiState,
    onMarkerClick: (Cinema?) -> Unit,
    onPositionChange: (DeviceLocation) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(uiState.lastLocation) {
        uiState.lastLocation?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newCameraPosition(
                    CameraPosition(
                        LatLng(it.latitude, it.longitude),
                        12f,
                        0f,
                        0f
                    )
                )
            )
        }
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            if (cameraPositionState.cameraMoveStartedReason == CameraMoveStartedReason.GESTURE) {
                cameraPositionState.position.target.let {
                    onPositionChange.invoke(DeviceLocation(it.latitude, it.longitude))
                }
            }
        }
    }

    GoogleMapsComponent(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = {
            onMarkerClick.invoke(null)
        }) {

        if (uiState.lastLocation != null) {
            CurrentLocationMarker(
                position = LatLng(uiState.lastLocation.latitude, uiState.lastLocation.longitude),
                iconRes = Res.drawable.ic_maps_marker_user
            ) {
                coroutineScope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newCameraPosition(
                            CameraPosition(it.position, 12f, 0f, 0f)
                        ),
                        durationMs = 400
                    )
                }
            }
        }

        uiState.cinemaList.forEach { cinema ->
            MapsMarker(
                position = LatLng(cinema.location.latitude, cinema.location.longitude),
                title = cinema.name,
                iconRes = Res.drawable.ic_maps_marker
            ) {
                onMarkerClick.invoke(cinema)
            }
        }
    }

}