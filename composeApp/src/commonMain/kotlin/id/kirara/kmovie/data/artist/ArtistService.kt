package id.kirara.kmovie.data.artist

interface ArtistService {

    suspend fun artistDetail(personId: Int): ArtistDetailModel

    suspend fun artistCredits(personId: Int): ArtistCreditsModel
}
