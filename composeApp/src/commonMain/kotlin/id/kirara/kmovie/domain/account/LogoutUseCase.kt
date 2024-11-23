package id.kirara.kmovie.domain.account

class LogoutUseCase(
    private val repository: AccountRepository,
    private val sessionSettings: SessionSettings
) {
    suspend fun execute() : Result<Boolean> {
        val result = repository.logout(sessionSettings.getSessionId())

        if(result.isSuccess) sessionSettings.removeSession()

        return result
    }
}