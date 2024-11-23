package id.kirara.kmovie.domain.movie

import id.kirara.kmovie.domain.artist.Credits
import id.kirara.kmovie.domain.favorite.MovieAccountState

interface MovieRepository {

    suspend fun getPopularMovie(): Result<List<PopularMovie>>

    suspend fun getNowPlayingMovie(page: Int = 1): Result<List<NowPlayingMovie>>

    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>

    suspend fun getMovieCredits(movieId: Int): Result<List<Credits>>

    suspend fun getMovieAccountState(movieId: Int): Result<MovieAccountState>
}