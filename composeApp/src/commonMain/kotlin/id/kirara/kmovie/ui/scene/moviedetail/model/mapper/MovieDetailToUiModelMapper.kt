package id.kirara.kmovie.ui.scene.moviedetail.model.mapper

import id.kirara.kmovie.domain.artist.Credits
import id.kirara.kmovie.domain.movie.MovieDetail
import id.kirara.kmovie.ui.scene.moviedetail.model.MovieDetailUiModel

class MovieDetailToUiModelMapper {
    fun map(from: MovieDetail, credits: List<Credits>) : MovieDetailUiModel {
        with(from){
            return MovieDetailUiModel(
                movieId = movieId,
                runtime = runtime,
                releaseDate = releaseDate,
                voteAverage = voteAverage,
                title = title,
                overview = overview,
                posterPath = posterPath,
                genre = genre,
                voteCount = voteCount,
                backdropPath = backdropPath,
                credits = credits
            )
        }
    }
}
