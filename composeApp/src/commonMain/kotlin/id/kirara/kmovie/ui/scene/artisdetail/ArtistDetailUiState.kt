package id.kirara.kmovie.ui.scene.artisdetail

import id.kirara.kmovie.ui.scene.artisdetail.model.ArtistDetailUiModel

data class ArtistDetailUiState(
    val isLoading: Boolean = true,
    val artistDetailData: ArtistDetailUiModel = ArtistDetailUiModel(),
    val error: String? = null
)