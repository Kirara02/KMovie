package id.kirara.kmovie.data.account

import id.kirara.kmovie.data.account.login.LoginRequestModel
import id.kirara.kmovie.data.account.login.LoginResponseModel
import id.kirara.kmovie.data.account.login.RequestTokenResponseModel
import id.kirara.kmovie.data.account.login.SessionRequestModel
import id.kirara.kmovie.data.account.login.SessionResponseModel

interface AccountService {

    suspend fun accountDetails(sessionId: String): AccountDetailModel

    //login
    suspend fun createRequestToken(): RequestTokenResponseModel

    suspend fun createRequestTokenWithLogin(requestModel: LoginRequestModel): LoginResponseModel

    suspend fun createSession(requestModel: SessionRequestModel): SessionResponseModel

    suspend fun logout(logoutRequestModel: LogoutRequestModel): LogoutResponseModel

}