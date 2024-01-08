package io.github.andremion.jobster.ui.navigation

import platform.Foundation.NSCharacterSet
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.Foundation.stringByAddingPercentEncodingWithAllowedCharacters
import platform.Foundation.stringByRemovingPercentEncoding
import platform.UIKit.UIApplication

actual fun navigateToUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url) ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}

actual fun encodeUrl(url: String): String =
    NSString.create(string = url).stringByAddingPercentEncodingWithAllowedCharacters(
        NSCharacterSet.alphanumericCharacterSet
    ) ?: url

actual fun decodeUrl(url: String): String =
    NSString.create(string = url).stringByRemovingPercentEncoding() ?: url
