package id.kirara.kmovie.data.tv

import id.kirara.kmovie.domain.account.SessionSettings
import id.kirara.kmovie.domain.artist.Credits
import id.kirara.kmovie.domain.favorite.TvAccountState
import id.kirara.kmovie.domain.tv.PopularTv
import id.kirara.kmovie.domain.tv.TopRatedTv
import id.kirara.kmovie.domain.tv.TvDetail
import id.kirara.kmovie.domain.tv.TvRepository
import id.kirara.kmovie.utils.resultOf

class TvRepositoryImpl(
    private val service: TvService,
    private val sessionSettings: SessionSettings
) : TvRepository {
    override suspend fun getPopularTv(): Result<List<PopularTv>> {
        return resultOf {
            service.popularTv().tvSeries.map { it.toDomain() }
        }
    }

    override suspend fun getTopRatedTv(): Result<List<TopRatedTv>> {
        return resultOf {
            service.topRatedTv().tvSeries.map { it.toDomain() }
        }
    }

    override suspend fun getTvDetail(tvId: Int): Result<TvDetail> {
        return resultOf {
            service.tvDetail(tvId).toDomain()
        }
    }

    override suspend fun getTvCredits(tvId: Int): Result<List<Credits>> {
        return resultOf {
            service.tvCredit(tvId).cast.map { it.toDomain() }
        }
    }

    override suspend fun getTvAccountState(tvId: Int): Result<TvAccountState> {
        return resultOf {
            val response = service.getTvState(
                sessionId = sessionSettings.getSessionId() ?: "", tvId
            )
            TvAccountState(response.favorite ?: false, response.rated)
        }
    }
}