package id.kirara.kmovie.ui.scene.account

import id.kirara.kmovie.domain.account.AccountDetail
import id.kirara.kmovie.domain.favorite.FavoriteMovie
import id.kirara.kmovie.domain.favorite.FavoriteTv

data class AccountUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val accountData: AccountDetail = AccountDetail()
)

data class FavoriteMovieUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val favoriteMovieData: List<FavoriteMovie> = emptyList()
)

data class FavoriteTvUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val favoriteTvData: List<FavoriteTv> = emptyList()
)

fun AccountDetail() : AccountDetail = AccountDetail(0, "", "", "")
