package id.kirara.kmovie.domain.favorite

data class MovieAccountState(
    val isFavorite: Boolean,
    val rating: Float?
)