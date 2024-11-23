package id.kirara.kmovie.domain.rate

class RemoveTvShowRatingUseCase(
    private val repository: RateRepository
) {
    suspend fun execute(tvShowId: Int) : Result<Unit> {
        return repository.removeTvShowRating(tvShowId)
    }
}