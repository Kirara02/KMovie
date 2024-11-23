package id.kirara.kmovie

import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import id.kirara.kmovie.di.init
import id.kirara.kmovie.utils.Action
import id.kirara.kmovie.utils.createStore
import org.koin.compose.KoinApplication
import platform.UIKit.UIViewController

fun mainViewController(): UIViewController {
    val uiViewController = ComposeUIViewController {
        KoinApplication(
            application = {
                init()
            }
        ) {
            App()
        }
    }
    Holder.viewController = uiViewController
    return uiViewController
}


internal object Holder {
    var viewController: UIViewController? = null
}

fun onBackGesture() {
    store.send(Action.OnBackPressed)
}

val store = CoroutineScope(SupervisorJob()).createStore()