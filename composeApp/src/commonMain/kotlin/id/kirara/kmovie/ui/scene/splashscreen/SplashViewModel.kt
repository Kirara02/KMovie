package id.kirara.kmovie.ui.scene.splashscreen

import id.kirara.kmovie.core.ViewModel
import id.kirara.kmovie.domain.account.AccountRepository

class SplashViewModel(
    repository: AccountRepository
) : ViewModel {
    val isLoggedIn = repository.getLoginState()
}