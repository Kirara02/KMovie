package id.kirara.kmovie.ui.scene.moviedetail

import id.kirara.kmovie.Logger
import id.kirara.kmovie.core.ViewModel
import id.kirara.kmovie.core.viewModelScope
import id.kirara.kmovie.domain.account.SessionSettings
import id.kirara.kmovie.domain.favorite.AddFavoriteUseCase
import id.kirara.kmovie.domain.favorite.GetMovieStateUseCase
import id.kirara.kmovie.domain.movie.MovieDetail
import id.kirara.kmovie.domain.movie.MovieRepository
import id.kirara.kmovie.domain.rate.RateMovieUseCase
import id.kirara.kmovie.domain.rate.RemoveMovieRatingUseCase
import id.kirara.kmovie.ui.scene.moviedetail.model.mapper.MovieDetailToUiModelMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MovieDetailViewModel(
    private val repository: MovieRepository,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val getMovieStateUseCase: GetMovieStateUseCase,
    private val sessionSettings: SessionSettings,
    private val rateMovieUseCase: RateMovieUseCase,
    private val removeMovieRatingUseCase: RemoveMovieRatingUseCase
) : ViewModel {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> get() = _isFavorite

    private val _uiRating = MutableStateFlow<Int?>(null)
    private val _actualRating = MutableStateFlow<Int?>(null)
    val rating = _uiRating.asStateFlow()

    private val mapper = MovieDetailToUiModelMapper()

    fun fetchData(movieId: Int) {
        viewModelScope.launch {
            val movieDetailResult = repository.getMovieDetail(movieId)
            val movieCreditResult = repository.getMovieCredits(movieId)

            movieDetailResult.onFailure {
                Logger.e(it.message.toString())
            }

            movieCreditResult.onFailure {
                Logger.e(it.message.toString())
            }
            if (movieDetailResult.isSuccess && movieCreditResult.isSuccess) {
                _uiState.update { uiState ->
                    uiState.copy(
                        isLoading = false,
                        movieDetailData = mapper.map(
                            from = movieDetailResult.getOrDefault(MovieDetail()),
                            credits = movieCreditResult.getOrDefault(listOf())
                        )
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

    fun addFavorite(mediaId: Int, mediaType: String, isFavorite: Boolean) {
        viewModelScope.launch {
            val accountId = sessionSettings.getAccountId()

            val result = addFavoriteUseCase.execute(
                accountId = accountId ?: 0,
                mediaId = mediaId,
                mediaType = mediaType,
                isFavorite = isFavorite
            )

            if(result.isSuccess) _isFavorite.value = isFavorite
        }
    }

    fun getMovieState(mediaId: Int) {
        viewModelScope.launch {
            val result = getMovieStateUseCase.execute(mediaId)

            if(result.isSuccess){
                _isFavorite.value = result.getOrNull()?.isFavorite ?: false
                result.getOrNull()?.rating?.roundToInt().also {
                    _uiRating.value = it
                    _actualRating.value = it
                }
            }
        }
    }

    private val _rateJob = MutableStateFlow<Job?>(null)

    fun rateMovie(rating: Int, movieId: Int) {
        if(_uiRating.value == rating){
            _uiRating.value = null
        } else {
            _uiRating.value = rating
        }

        _rateJob.value?.cancel()

        if(_rateJob.value?.isActive == true) return

        _rateJob.value = viewModelScope.launch {
            delay(500)

            if (_actualRating.value == rating){
                removeMovieRatingUseCase.execute(movieId).onSuccess {
                    _actualRating.value = null
                }
            } else {
                val isRateSuccess = rateMovieUseCase.execute(rating, movieId)

                if (isRateSuccess.value) {
                    _actualRating.value = rating
                }
            }

            _uiRating.value = _actualRating.value
        }
    }

}