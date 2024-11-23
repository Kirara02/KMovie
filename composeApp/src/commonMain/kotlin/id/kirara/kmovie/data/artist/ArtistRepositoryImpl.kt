package id.kirara.kmovie.data.artist

import id.kirara.kmovie.domain.artist.ArtistCredit
import id.kirara.kmovie.domain.artist.ArtistDetail
import id.kirara.kmovie.domain.artist.ArtistRepository
import id.kirara.kmovie.utils.resultOf

class ArtistRepositoryImpl(
    private val service: ArtistService
) : ArtistRepository {
    override suspend fun getArtistDetail(personId: Int): Result<ArtistDetail> {
        return resultOf {
            service.artistDetail(personId).toDomain()
        }
    }

    override suspend fun getArtistCredits(personId: Int): Result<List<ArtistCredit>> {
        return resultOf {
            service.artistCredits(personId).cast?.map { it.toDomain() } ?: listOf()
        }
    }
}