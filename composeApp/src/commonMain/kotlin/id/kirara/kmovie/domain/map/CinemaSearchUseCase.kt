package id.kirara.kmovie.domain.map

import id.kirara.kmovie.ui.scene.map.Cinema

class CinemaSearchUseCase(
    private val repository: MapRepository
) {
    suspend fun execute(
        latitude: Double,
        longitude: Double,
    ) : Result<List<Cinema>> {
        return repository.getCinemas(latitude, longitude)
    }
}