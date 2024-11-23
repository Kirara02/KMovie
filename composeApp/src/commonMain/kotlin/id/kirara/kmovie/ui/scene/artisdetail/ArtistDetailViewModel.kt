package id.kirara.kmovie.ui.scene.artisdetail

import id.kirara.kmovie.core.ViewModel
import id.kirara.kmovie.core.viewModelScope
import id.kirara.kmovie.domain.artist.ArtistDetail
import id.kirara.kmovie.domain.artist.ArtistRepository
import id.kirara.kmovie.ui.scene.artisdetail.model.mapper.ArtistDetailUiModelMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtistDetailViewModel(
    private val repository: ArtistRepository
) : ViewModel {

    private val _uiState = MutableStateFlow(ArtistDetailUiState())
    val uiState: StateFlow<ArtistDetailUiState> = _uiState

    private val mapper = ArtistDetailUiModelMapper()

    fun fetchData(actorId: Int) {
        viewModelScope.launch {
            val artistDetailResult = repository.getArtistDetail(actorId)
            val artistCreditResult = repository.getArtistCredits(actorId)

            if (artistDetailResult.isSuccess && artistCreditResult.isSuccess) {
                _uiState.update { uiState ->
                    uiState.copy(
                        isLoading = false,
                        artistDetailData = mapper.map(
                            from = artistDetailResult.getOrDefault(ArtistDetail()),
                            credit = artistCreditResult.getOrDefault(listOf())
                        )
                    )
                }
            }else {
                _uiState.update { uiState ->
                    uiState.copy(
                        isLoading = false,
                        error = "ERROR"
                    )
                }
            }
        }
    }

}