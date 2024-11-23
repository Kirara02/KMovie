package id.kirara.kmovie.ui.scene.map

import id.kirara.kmovie.core.ViewModel
import id.kirara.kmovie.core.viewModelScope
import id.kirara.kmovie.domain.location.DeviceLocation
import id.kirara.kmovie.domain.location.LocationRepository
import id.kirara.kmovie.domain.map.CinemaSearchUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel(
    private val cinemaSearchUseCase: CinemaSearchUseCase,
    private val locationService: LocationRepository
) : ViewModel {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState

    private var job: Job? = null

    fun loadForecastWithLocation(){
        viewModelScope.launch {
            val location = locationService.getCurrentLocation()
            _uiState.value = _uiState.value.copy(lastLocation = location)
            getCinemasOnLocation(location)
        }
    }

    fun getUpdates(location: DeviceLocation){
        job?.cancel()
        job = viewModelScope.launch {
            delay(1000)
            getCinemasOnLocation(location)
            job = null
        }
    }

    private suspend fun getCinemasOnLocation(coordinates: DeviceLocation?){
        if (coordinates == null) return
            val result = cinemaSearchUseCase.execute(
                latitude = coordinates.latitude,
                longitude = coordinates.longitude
            )

            result.onSuccess { cinemas ->
                _uiState.update {
                    it.copy(
                        cinemaList = cinemas,
                        lastLocation = coordinates
                    )
                }
            }

    }

    fun setSelectedCinema(cinema: Cinema?){
        val deviceLocation = cinema?.let {
            DeviceLocation(it.location.latitude, it.location.longitude)
        }
        _uiState.update {
            it.copy(
                selectedCinema = cinema,
                lastLocation = deviceLocation
            )
        }
    }
}