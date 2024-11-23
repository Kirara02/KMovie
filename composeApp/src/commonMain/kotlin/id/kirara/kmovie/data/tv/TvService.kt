package id.kirara.kmovie.data.tv

import id.kirara.kmovie.data.artist.CreditsModel
import id.kirara.kmovie.data.favorite.AccountStateResponseModel

interface TvService {
    suspend fun popularTv(): PopularTvModel

    suspend fun topRatedTv(): TopRatedTvModel

    suspend fun tvDetail(tvId: Int): TvDetailModel

    suspend fun tvCredit(tvId: Int): CreditsModel

    suspend fun getTvState(sessionId: String, tvId: Int): AccountStateResponseModel
}