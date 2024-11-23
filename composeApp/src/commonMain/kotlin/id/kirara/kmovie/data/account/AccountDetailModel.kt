package id.kirara.kmovie.data.account

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class AccountDetailModel(
    @SerialName("avatar") val avatar: Avatar,
    @SerialName("id") val id: Int,
    @SerialName("include_adult") val includeAdult: Boolean,
    @SerialName("iso_3166_1") val country: String,
    @SerialName("iso_639_1") val language: String,
    @SerialName("name") val name: String,
    @SerialName("username") val username: String
) {
    @Serializable
    data class Avatar(
        @SerialName("gravatar")
        val gravatar: Gravatar,
        @SerialName("tmdb")
        val tmdb: Tmdb
    ) {
        @Serializable
        data class Gravatar(
            @SerialName("hash")
            val hash: String
        )

        @Serializable
        data class Tmdb(
            @SerialName("avatar_path")
            val avatarPath: String?
        )
    }
}