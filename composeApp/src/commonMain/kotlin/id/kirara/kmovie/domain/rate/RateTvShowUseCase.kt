package id.kirara.kmovie.domain.rate

import id.kirara.kmovie.domain.favorite.IsRateSuccess

class RateTvShowUseCase(
    private val repository: RateRepository
) {
    suspend fun execute(rating: Int, tvId: Int) : IsRateSuccess {
        val result = repository.rateTvShow(rating.toDouble(), tvId)

        result.onSuccess {
            return IsRateSuccess(true)
        }

        return IsRateSuccess(false)
    }
}