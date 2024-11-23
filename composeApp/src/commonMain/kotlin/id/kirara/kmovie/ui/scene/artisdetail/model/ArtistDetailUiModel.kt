package id.kirara.kmovie.ui.scene.artisdetail.model

import id.kirara.kmovie.domain.artist.ArtistCredit

data class ArtistDetailUiModel(
    val name: String = "",
    val biography: String = "",
    val birthday: String = "",
    val placeOfBirth: String = "",
    val profilePath: String = "",
    val credit: List<ArtistCredit> = listOf()
)