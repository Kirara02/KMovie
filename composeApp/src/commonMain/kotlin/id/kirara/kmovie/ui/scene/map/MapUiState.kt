package id.kirara.kmovie.ui.scene.map

import id.kirara.kmovie.domain.location.DeviceLocation

data class MapUiState(
    val lastLocation: DeviceLocation? = null,
    val userLocation: DeviceLocation? = null,
    val cinemaList: List<Cinema> = emptyList(),
    val selectedCinema: Cinema? = null,
    val dialogVisibility: Boolean = false,
    val searchVisibility: Boolean = false,
)

data class Cinema(
    val name: String,
    val description: String,
    val location: DeviceLocation
)