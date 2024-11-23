package id.kirara.kmovie.domain.rate

interface RateRepository {
    suspend fun rateMovie(rating: Double, movieId: Int) : Result<Boolean>
    suspend fun rateTvShow(rating: Double, tvId: Int) : Result<Boolean>
    suspend fun removeMovieRating(movieId: Int): Result<Unit>
    suspend fun removeTvShowRating(tvShowId: Int): Result<Unit>
}