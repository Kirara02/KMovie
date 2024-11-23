package id.kirara.kmovie.data.map

import id.kirara.kmovie.domain.location.DeviceLocation
import id.kirara.kmovie.domain.map.MapRepository
import id.kirara.kmovie.ui.scene.map.Cinema
import id.kirara.kmovie.utils.resultOf

class MapRepositoryImpl(
    private val service: NominatimService
) : MapRepository {
    override suspend fun getCinemas(latitude: Double, longitude: Double): Result<List<Cinema>> {
        return resultOf {
            service.getCinemas(latitude, longitude).map {
                Cinema(it.name, it.displayName, DeviceLocation(it.lat, it.lon))
            }
        }
    }

}