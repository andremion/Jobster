/*
 *    Copyright 2024. André Luiz Oliveira Rêgo
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
