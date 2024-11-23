package id.kirara.kmovie.domain.rate

import id.kirara.kmovie.domain.favorite.IsRateSuccess

class RateMovieUseCase(
    private val repository: RateRepository
) {
    suspend fun execute(rating: Int, movieId: Int) : IsRateSuccess {
        val result = repository.rateMovie(rating.toDouble(), movieId)

        result.onSuccess {
            return IsRateSuccess(true)
        }

        return IsRateSuccess(false)
    }
}