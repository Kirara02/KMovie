package id.kirara.kmovie.domain.account

data class AccountDetail(
    val id: Int,
    val fullName: String,
    val username: String,
    val country: String,
    val profilePath: String? = null
)