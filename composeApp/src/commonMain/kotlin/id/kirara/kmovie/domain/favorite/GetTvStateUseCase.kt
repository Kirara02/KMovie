package id.kirara.kmovie.domain.favorite

import id.kirara.kmovie.domain.tv.TvRepository

class GetTvStateUseCase(
    private val repository: TvRepository
) {
    suspend fun execute(mediaId: Int) : Result<TvAccountState>{
        return repository.getTvAccountState(mediaId)
    }
}