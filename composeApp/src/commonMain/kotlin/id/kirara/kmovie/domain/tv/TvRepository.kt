package id.kirara.kmovie.domain.tv

import id.kirara.kmovie.domain.artist.Credits
import id.kirara.kmovie.domain.favorite.TvAccountState

interface TvRepository {
    suspend fun getPopularTv(): Result<List<PopularTv>>

    suspend fun getTopRatedTv(): Result<List<TopRatedTv>>

    suspend fun getTvDetail(tvId: Int): Result<TvDetail>

    suspend fun getTvCredits(tvId: Int): Result<List<Credits>>

    suspend fun getTvAccountState(tvId: Int): Result<TvAccountState>
}