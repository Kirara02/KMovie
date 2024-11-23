package id.kirara.kmovie.data.rate

import id.kirara.kmovie.domain.account.SessionSettings
import id.kirara.kmovie.domain.rate.RateRepository
import id.kirara.kmovie.utils.resultOf

class RateRepositoryImpl(
    private val service: RateService,
    private val sessionSettings: SessionSettings
) : RateRepository{
    override suspend fun rateMovie(rating: Double, movieId: Int): Result<Boolean> {
        val result = resultOf {
            service.rateMovie(
                rating = RateDto(value = rating),
                movieId = movieId,
                sessionId = sessionSettings.getSessionId() ?: ""
            )
        }

        return result.map { it.success }
    }

    override suspend fun rateTvShow(rating: Double, tvId: Int): Result<Boolean> {
        val result = resultOf {
            service.rateTvShow(
                rating = RateDto(value = rating),
                tvShowId = tvId,
                sessionId = sessionSettings.getSessionId() ?: ""
            )
        }

        return result.map { it.success }
    }

    override suspend fun removeMovieRating(movieId: Int): Result<Unit> {
        return resultOf {
            service.removeMovieRating(movieId, sessionSettings.getSessionId()!!)
        }
    }

    override suspend fun removeTvShowRating(tvShowId: Int): Result<Unit> {
        return resultOf {
            service.removeTvShowRating(tvShowId, sessionSettings.getSessionId()!!)
        }
    }
}