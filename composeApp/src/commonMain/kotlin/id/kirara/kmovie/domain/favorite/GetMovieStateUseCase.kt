package id.kirara.kmovie.domain.favorite

import id.kirara.kmovie.domain.movie.MovieRepository

class GetMovieStateUseCase(
    private val repository: MovieRepository
) {
    suspend fun execute(mediaId: Int) : Result<MovieAccountState> {
        return repository.getMovieAccountState(mediaId)
    }
}