package id.kirara.kmovie.data.movie

import id.kirara.kmovie.data.artist.CreditsModel
import id.kirara.kmovie.data.favorite.AccountStateResponseModel

interface MovieService {

    suspend fun popularMovie() : PopularMovieModel

    suspend fun nowPlayingMovie(page: Int = 1) : NowPlayingMovieModel

    suspend fun movieDetail(movieId: Int) : MovieDetailModel

    suspend fun movieCredits(movieId: Int) : CreditsModel

    suspend fun getMovieState(sessionId: String, movieId: Int) : AccountStateResponseModel
}