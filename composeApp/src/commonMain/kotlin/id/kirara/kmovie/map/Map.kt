package id.kirara.kmovie.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.kirara.kmovie.domain.location.DeviceLocation
import id.kirara.kmovie.ui.scene.map.Cinema
import id.kirara.kmovie.ui.scene.map.MapUiState

@Composable
expect fun Map(
    modifier: Modifier,
    uiState: MapUiState,
    onMarkerClick: (Cinema?) -> Unit,
    onPositionChange: (DeviceLocation) -> Unit
)