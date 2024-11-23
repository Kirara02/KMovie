package id.kirara.kmovie.domain.account

class GetAccountDetailUseCase(
    private val repository: AccountRepository
) {
    suspend fun execute() : Result<AccountDetail> {
        return repository.getAccountDetail()
    }
}