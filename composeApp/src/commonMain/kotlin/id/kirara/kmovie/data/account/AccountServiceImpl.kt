package id.kirara.kmovie.data.account

import id.kirara.kmovie.data.account.login.*
import id.kirara.kmovie.utils.Constants.SESSION_ID
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AccountServiceImpl(
    private val client: HttpClient
) : AccountService {
    override suspend fun accountDetails(sessionId: String): AccountDetailModel {
        return client.get(ACCOUNT) {
            url {
                parameters.append(SESSION_ID, sessionId)
            }
        }.body()
    }

    //login

    override suspend fun createRequestToken(): RequestTokenResponseModel {
        return client.get(REQUEST_TOKEN).body()
    }

    override suspend fun createRequestTokenWithLogin(requestModel: LoginRequestModel): LoginResponseModel {
        return client.post(LOGIN) {
            contentType(ContentType.Application.Json)
            setBody(requestModel)
        }.body()
    }

    override suspend fun createSession(requestModel: SessionRequestModel): SessionResponseModel {
        return client.post(SESSION) {
            contentType(ContentType.Application.Json)
            setBody(requestModel)
        }.body()
    }


    override suspend fun logout(logoutRequestModel: LogoutRequestModel): LogoutResponseModel {
        return client.delete(LOGOUT) {
            setBody(logoutRequestModel)
            contentType(ContentType.Application.Json)
        }.body()
    }

    companion object {
        const val ACCOUNT = "account"
        const val REQUEST_TOKEN = "authentication/token/new"
        const val LOGIN = "authentication/token/validate_with_login"
        const val SESSION = "authentication/session/new"
        const val LOGOUT = "authentication/session"
    }
}