package id.kirara.kmovie.domain.tv

data class TopRatedTv(
    val tvId: Int,
    val voteAverage: Double,
    val title: String,
    val posterPath: String,
)
