package id.kirara.kmovie.data.favorite

import id.kirara.kmovie.domain.account.SessionSettings
import id.kirara.kmovie.domain.favorite.FavoriteMovie
import id.kirara.kmovie.domain.favorite.FavoriteRepository
import id.kirara.kmovie.domain.favorite.FavoriteTv
import id.kirara.kmovie.utils.resultOf

class FavoriteRepositoryImpl(
    private val service: FavoriteService,
    private val sessionSettings: SessionSettings
) : FavoriteRepository {

    override suspend fun addFavorite(
        accountId: Int,
        addFavoriteRequestModel: AddFavoriteRequestModel
    ): Result<AddFavoriteResponseModel> {
        return resultOf {
            service.addFavorite(
                accountId = accountId,
                addFavoriteRequestModel = addFavoriteRequestModel,
                sessionId = sessionSettings.getSessionId() ?: "",
            )
        }
    }

    override suspend fun getFavoriteMovie(
        accountId: Int,
        sessionId: String
    ): Result<List<FavoriteMovie>> {
        return resultOf {
            service.getFavoriteMovie(
                accountId = accountId,
                sessionId = sessionId
            ).favMovies.map { it.toDomain() }
        }
    }

    override suspend fun getFavoriteTv(
        accountId: Int,
        sessionId: String
    ): Result<List<FavoriteTv>> {
        return resultOf {
            service.getFavoriteTv(
                accountId = accountId,
                sessionId = sessionId
            ).favTv.map { it.toDomain() }
        }
    }
}