package io.github.andremion.jobster.ui.navigation

import android.content.Intent
import android.net.Uri
import io.github.andremion.jobster.MainApp
import java.net.URLDecoder
import java.net.URLEncoder

actual fun navigateToUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    MainApp.INSTANCE.startActivity(intent)
}

private const val Encoding = "UTF-8"

actual fun encodeUrl(url: String): String {
    return URLEncoder.encode(url, Encoding)
}

actual fun decodeUrl(url: String): String =
    URLDecoder.decode(url, Encoding)
