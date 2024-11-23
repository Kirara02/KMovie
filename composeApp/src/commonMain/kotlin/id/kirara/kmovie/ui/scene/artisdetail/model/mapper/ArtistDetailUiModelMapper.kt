package id.kirara.kmovie.ui.scene.artisdetail.model.mapper

import id.kirara.kmovie.domain.artist.ArtistCredit
import id.kirara.kmovie.domain.artist.ArtistDetail
import id.kirara.kmovie.ui.scene.artisdetail.model.ArtistDetailUiModel

class ArtistDetailUiModelMapper {
    fun map(from: ArtistDetail, credit: List<ArtistCredit>): ArtistDetailUiModel {
        with(from) {
            return ArtistDetailUiModel(
                name = name,
                biography = biography,
                birthday = birthday,
                placeOfBirth = placeOfBirth,
                profilePath = profilePath,
                credit = credit
            )
        }
    }
}