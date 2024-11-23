package id.kirara.kmovie.data.account.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionResponseModel(
    @SerialName("success") val success: Boolean,
    @SerialName("session_id") val sessionId: String
)