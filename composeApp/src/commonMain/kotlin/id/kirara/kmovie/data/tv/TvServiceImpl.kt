package id.kirara.kmovie.data.tv

import id.kirara.kmovie.data.artist.CreditsModel
import id.kirara.kmovie.data.favorite.AccountStateResponseModel
import id.kirara.kmovie.utils.Constants.SESSION_ID
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TvServiceImpl(
    private val client: HttpClient
) : TvService {
    override suspend fun popularTv(): PopularTvModel {
        return client.get(POPULAR_TV).body()
    }

    override suspend fun topRatedTv(): TopRatedTvModel {
        return client.get(TOP_RATED_TV).body()
    }

    override suspend fun tvDetail(tvId: Int): TvDetailModel {
        return client.get("tv/$tvId").body()
    }

    override suspend fun tvCredit(tvId: Int): CreditsModel {
        return client.get("tv/$tvId/credits").body()
    }

    override suspend fun getTvState(sessionId: String, tvId: Int): AccountStateResponseModel {
        return client.get("tv/$tvId/account_states"){
            url {
                parameters.append(SESSION_ID, sessionId)
            }
        }.body()
    }

    companion object {
        const val POPULAR_TV = "tv/popular"
        const val TOP_RATED_TV = "tv/top_rated"
    }
}