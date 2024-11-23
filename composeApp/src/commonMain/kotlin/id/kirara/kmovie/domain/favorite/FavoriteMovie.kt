package id.kirara.kmovie.domain.favorite

data class FavoriteMovie(
    val movieId: Int = 0,
    val releaseDate: String = "",
    val voteAverage: Double = 0.0,
    val title: String = "",
    val posterPath: String = ""
)