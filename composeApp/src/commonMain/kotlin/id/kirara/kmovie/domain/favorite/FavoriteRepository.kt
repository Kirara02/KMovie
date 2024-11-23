package id.kirara.kmovie.domain.favorite

import id.kirara.kmovie.data.favorite.AddFavoriteRequestModel
import id.kirara.kmovie.data.favorite.AddFavoriteResponseModel

interface FavoriteRepository {

    suspend fun addFavorite(
        accountId: Int,
        addFavoriteRequestModel: AddFavoriteRequestModel
    ) : Result<AddFavoriteResponseModel>

    suspend fun getFavoriteMovie(accountId: Int, sessionId: String) : Result<List<FavoriteMovie>>

    suspend fun getFavoriteTv(accountId: Int, sessionId: String) : Result<List<FavoriteTv>>

}