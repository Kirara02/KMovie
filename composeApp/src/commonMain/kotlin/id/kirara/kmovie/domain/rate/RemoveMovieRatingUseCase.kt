package id.kirara.kmovie.domain.rate

class RemoveMovieRatingUseCase(
    private val repository: RateRepository
) {
    suspend fun execute(movieId: Int) : Result<Unit> {
        return repository.removeMovieRating(movieId)
    }
}