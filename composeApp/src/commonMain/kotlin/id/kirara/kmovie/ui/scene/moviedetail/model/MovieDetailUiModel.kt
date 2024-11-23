package id.kirara.kmovie.ui.scene.moviedetail.model

import id.kirara.kmovie.domain.artist.Credits

data class MovieDetailUiModel(
    val movieId: Int = 0,
    val runtime: Int = 0,
    val releaseDate: String = "",
    val voteAverage: Double = 0.0,
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val genre: String = "",
    val voteCount: Int = 0,
    val backdropPath: String = "",
    val credits: List<Credits> = listOf(),
    val homepage: String = ""
)
