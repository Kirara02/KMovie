package id.kirara.kmovie.domain.map

import id.kirara.kmovie.ui.scene.map.Cinema

interface MapRepository {
    suspend fun getCinemas(
        latitude: Double,
        longitude: Double
    ) : Result<List<Cinema>>
}