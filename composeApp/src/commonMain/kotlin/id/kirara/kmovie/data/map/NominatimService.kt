package id.kirara.kmovie.data.map

interface NominatimService {
    suspend fun getCinemas(latitude: Double, longitude: Double) : List<OSMObject>
}