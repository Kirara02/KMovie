package id.kirara.kmovie.ui.scene.tv

import id.kirara.kmovie.core.ViewModel
import id.kirara.kmovie.core.viewModelScope
import id.kirara.kmovie.domain.tv.TvRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TvViewModel(
    private val repository: TvRepository
) : ViewModel {

    private val _uiState = MutableStateFlow(TvUiState())
    val uiState: StateFlow<TvUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            val popularTvResult = repository.getPopularTv()
            val topRatedResult = repository.getTopRatedTv()

            if (popularTvResult.isSuccess && topRatedResult.isSuccess) {
                _uiState.update { uiState ->
                    uiState.copy(
                        isLoading = false,
                        popularTvData = popularTvResult.getOrDefault(listOf()),
                        topRatedTvData = topRatedResult.getOrDefault(listOf())
                    )
                }
            } else {
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