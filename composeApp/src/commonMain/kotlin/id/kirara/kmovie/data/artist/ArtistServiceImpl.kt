package id.kirara.kmovie.data.artist

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ArtistServiceImpl(
    private val client: HttpClient
) : ArtistService {
    override suspend fun artistDetail(personId: Int): ArtistDetailModel {
        return client.get("person/$personId").body()
    }

    override suspend fun artistCredits(personId: Int): ArtistCreditsModel {
        return client.get("person/$personId/combined_credits").body()
    }
}
