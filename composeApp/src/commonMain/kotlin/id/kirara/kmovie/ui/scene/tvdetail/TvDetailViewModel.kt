package id.kirara.kmovie.ui.scene.tvdetail

import id.kirara.kmovie.core.ViewModel
import id.kirara.kmovie.core.viewModelScope
import id.kirara.kmovie.domain.account.SessionSettings
import id.kirara.kmovie.domain.favorite.AddFavoriteUseCase
import id.kirara.kmovie.domain.favorite.GetTvStateUseCase
import id.kirara.kmovie.domain.rate.RateTvShowUseCase
import id.kirara.kmovie.domain.rate.RemoveTvShowRatingUseCase
import id.kirara.kmovie.domain.tv.TvDetail
import id.kirara.kmovie.domain.tv.TvRepository
import id.kirara.kmovie.ui.scene.tvdetail.model.mapper.TvDetailToUiModelMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class TvDetailViewModel(
    private val repository: TvRepository,
    private val sessionSettings: SessionSettings,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val getTvStateUseCase: GetTvStateUseCase,
    private val rateTvShowUseCase: RateTvShowUseCase,
    private val removeTvShowRatingUseCase: RemoveTvShowRatingUseCase
) : ViewModel {

    private val _uiState = MutableStateFlow(TvDetailUiState())
    val uiState: StateFlow<TvDetailUiState> = _uiState

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val _uiRating = MutableStateFlow<Int?>(null)
    private val _actualRating = MutableStateFlow<Int?>(null)
    val rating = _uiRating.asStateFlow()

    private val mapper = TvDetailToUiModelMapper()

    fun fetchData(tvId: Int) {
        viewModelScope.launch {
            val tvDetailResult = repository.getTvDetail(tvId)
            val tvCreditResult = repository.getTvCredits(tvId)

            if (tvDetailResult.isSuccess && tvCreditResult.isSuccess) {
                _uiState.update { uiState ->
                    uiState.copy(
                        isLoading = false,
                        tvDetailData = mapper.map(
                            from = tvDetailResult.getOrDefault(TvDetail()),
                            credit = tvCreditResult.getOrDefault(listOf())
                        )
                    )
                }
            } else {
                _uiState.update { uiState ->
                    uiState.copy(isLoading = false, error = "ERROR")
                }
            }
        }
    }


    fun addFavorite(mediaId: Int, mediaType: String, isFavorite: Boolean) {
        viewModelScope.launch {
            val accountId = sessionSettings.getAccountId()
            val result = addFavoriteUseCase.execute(
                accountId = accountId ?: 0,
                mediaId = mediaId,
                mediaType = mediaType,
                isFavorite = isFavorite
            )

            if (result.isSuccess) _isFavorite.value = isFavorite
        }
    }

    fun getTvState(tvId: Int) {
        viewModelScope.launch {
            val result = getTvStateUseCase.execute(tvId)

            if (result.isSuccess) {
                _isFavorite.value = result.getOrNull()?.isFavorite ?: false
                result.getOrNull()?.rating?.roundToInt().also {
                    _uiRating.value = it
                    _actualRating.value = it
                }
            }
        }
    }

    private val _rateJob = MutableStateFlow<Job?>(null)

    fun rateTvShow(rating: Int, tvShowId: Int) {
        if (_uiRating.value == rating) {
            _uiRating.value = null
        } else {
            _uiRating.value = rating
        }

        _rateJob.value?.cancel()

        if (_rateJob.value?.isActive == true) return

        _rateJob.value = viewModelScope.launch {
            delay(500)

            if (_actualRating.value == rating) {
                removeTvShowRatingUseCase.execute(tvShowId).onSuccess {
                    _actualRating.value = null
                }
            } else {
                val isRateSuccess = rateTvShowUseCase.execute(rating, tvShowId)

                if (isRateSuccess.value) {
                    _actualRating.value = rating
                }
            }

            _uiRating.value = _actualRating.value
        }
    }

}