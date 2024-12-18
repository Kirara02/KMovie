package id.kirara.kmovie.domain.artist

data class ArtistDetail(
    val name: String = "",
    val biography: String = "",
    val birthday: String = "",
    val placeOfBirth: String = "",
    val profilePath: String = "",
)

data class ArtistCredit(
    val imagePath: String? = null,
    val name: String? = null,
    val character: String? = null,
    val voteAverage: Double? = null,
    val releaseDate: String? = null,
    val id: Int? = null,
    val mediaType: String? = null
)