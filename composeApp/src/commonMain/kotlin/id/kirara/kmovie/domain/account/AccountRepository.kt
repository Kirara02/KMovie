package id.kirara.kmovie.domain.account

import id.kirara.kmovie.data.account.LoginState
import id.kirara.kmovie.data.account.login.LoginResponseModel
import kotlinx.coroutines.flow.StateFlow

interface AccountRepository {
    suspend fun getAccountDetail(): Result<AccountDetail>

    suspend fun login(username: String, password: String): Result<LoginResponseModel>

    fun getLoginState(): StateFlow<LoginState>

    suspend fun logout(sessionId: String?): Result<Boolean>
}