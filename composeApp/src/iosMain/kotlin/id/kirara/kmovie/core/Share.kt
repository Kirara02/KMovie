package id.kirara.kmovie.core

import id.kirara.kmovie.Holder
import platform.UIKit.UIActivityViewController

actual fun share(context: PlatformContext, text: String) {
    val controller = UIActivityViewController(listOf(text), null)

    Holder.viewController?.presentViewController(controller, true, null)
}