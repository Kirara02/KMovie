package id.kirara.kmovie.data.rate

interface RateService {
    suspend fun rateMovie(rating: RateDto, movieId: Int, sessionId: String): RateResponse
    suspend fun rateTvShow(rating: RateDto, tvShowId: Int, sessionId: String): RateResponse
    suspend fun removeMovieRating(movieId: Int, sessionId: String)
    suspend fun removeTvShowRating(tvShowId: Int, sessionId: String)
}