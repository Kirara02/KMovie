package id.kirara.kmovie.ui.scene.login

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val loginError: String? = null
)