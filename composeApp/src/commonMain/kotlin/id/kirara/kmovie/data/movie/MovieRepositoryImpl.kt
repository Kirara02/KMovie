package id.kirara.kmovie.data.movie

import id.kirara.kmovie.domain.account.SessionSettings
import id.kirara.kmovie.domain.artist.Credits
import id.kirara.kmovie.domain.favorite.MovieAccountState
import id.kirara.kmovie.domain.movie.MovieDetail
import id.kirara.kmovie.domain.movie.PopularMovie
import id.kirara.kmovie.domain.movie.MovieRepository
import id.kirara.kmovie.domain.movie.NowPlayingMovie
import id.kirara.kmovie.utils.resultOf

class MovieRepositoryImpl(
    private val service: MovieService,
    private val sessionSettings: SessionSettings
) : MovieRepository {
    override suspend fun getPopularMovie(): Result<List<PopularMovie>> {
        return resultOf {
            service.popularMovie().movies.map { it.toDomain() }
        }
    }

    override suspend fun getNowPlayingMovie(page: Int): Result<List<NowPlayingMovie>> {
        return resultOf {
            service.nowPlayingMovie(page).movies.map { it.toDomain() }
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return resultOf {
            service.movieDetail(movieId).toDomain()
        }
    }

    override suspend fun getMovieCredits(movieId: Int): Result<List<Credits>> {
        return resultOf {
            service.movieCredits(movieId).cast.map { it.toDomain() }
        }
    }

    override suspend fun getMovieAccountState(movieId: Int): Result<MovieAccountState> {
        return resultOf {
            val response = service.getMovieState(
                sessionSettings.getSessionId() ?: "",
                movieId
            )

            MovieAccountState(response.favorite ?: false, response.rated)
        }
    }

}