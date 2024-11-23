package id.kirara.kmovie.ui.scene.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import id.kirara.kmovie.core.ViewModel
import id.kirara.kmovie.core.viewModelScope
import id.kirara.kmovie.domain.account.AccountRepository
import id.kirara.kmovie.domain.account.GetAccountDetailUseCase
import id.kirara.kmovie.domain.account.SessionSettings
import id.kirara.kmovie.network.exception.NetworkException
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AccountRepository,
    private val sessionSettings: SessionSettings,
    private val getAccountDetailUseCase: GetAccountDetailUseCase
) :ViewModel {

    val isLoggedIn = repository.getLoginState()
    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onUserNameChange(username: String){
        loginUiState = loginUiState.copy(userName = username, loginError = null)
    }

    fun onPasswordChange(password: String){
        loginUiState = loginUiState.copy(password = password, loginError = null)
    }

    private fun validateLoginForm() =
        loginUiState.userName.isNotBlank() && loginUiState.password.isNotBlank()

    fun login(){
        viewModelScope.launch {
            if(!validateLoginForm()){
                loginUiState = loginUiState.copy(loginError = "Email and password cannot be empty")
                return@launch
            }
            loginUiState = loginUiState.copy(isLoading = true, loginError = null)
            repository.login(
                loginUiState.userName,
                loginUiState.password
            ).onSuccess {
                getAccountDetail()
            }.onFailure {
                val error = it as? NetworkException
                loginUiState = loginUiState.copy(isLoading = false, loginError = error?.networkError?.statusMessage)
            }
        }
    }

    private fun getAccountDetail(){
        viewModelScope.launch {
            val result = getAccountDetailUseCase.execute()

            if(result.isSuccess){
                sessionSettings.setAccountId(result.getOrNull()?.id ?: 0)
                loginUiState = loginUiState.copy(isLoading = false, isSuccessLogin = true)
            }
        }
    }
}