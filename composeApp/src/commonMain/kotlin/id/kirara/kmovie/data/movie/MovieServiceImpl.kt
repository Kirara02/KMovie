package id.kirara.kmovie.data.movie

import id.kirara.kmovie.data.artist.CreditsModel
import id.kirara.kmovie.data.favorite.AccountStateResponseModel
import id.kirara.kmovie.utils.Constants.SESSION_ID
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class  MovieServiceImpl(
    private val client: HttpClient
) : MovieService {
    override suspend fun popularMovie(): PopularMovieModel {
        return client.get(POPULAR_MOVIE).body()
    }

    override suspend fun nowPlayingMovie(page: Int): NowPlayingMovieModel {
        return client.get(NOW_PLAYING_MOVIE){
          url{
              parameters.append("page",page.toString())
          }
        }.body()
    }

    override suspend fun movieDetail(movieId: Int): MovieDetailModel {
        return client.get("movie/$movieId").body()
    }

    override suspend fun movieCredits(movieId: Int): CreditsModel {
        return client.get("movie/$movieId/credits").body()
    }

    override suspend fun getMovieState(sessionId: String, movieId: Int): AccountStateResponseModel {
        return client.get("movie/$movieId/account_states") {
            contentType(ContentType.Application.Json)
            url {
                parameters.append(SESSION_ID, sessionId)
            }
        }.body()
    }

    companion object {
        const val POPULAR_MOVIE = "movie/popular"
        const val NOW_PLAYING_MOVIE = "movie/now_playing"
    }

}