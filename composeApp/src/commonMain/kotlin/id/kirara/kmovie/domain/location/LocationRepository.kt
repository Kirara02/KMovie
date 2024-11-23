package id.kirara.kmovie.domain.location

interface LocationRepository {
    suspend fun getCurrentLocation(): DeviceLocation
}