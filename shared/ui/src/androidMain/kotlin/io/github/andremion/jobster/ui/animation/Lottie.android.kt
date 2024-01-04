package io.github.andremion.jobster.ui.animation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.io.BufferedReader

@Composable
actual fun rememberLottieData(spec: LottieCompositionSpec): String =
    LocalContext.current.let { context ->
        remember {
            when (spec) {
                is LottieCompositionSpec.RawRes -> {
                    context.loadRawData(spec)
                }
            }
        }
    }

@SuppressLint("DiscouragedApi")
private fun Context.loadRawData(rawRes: LottieCompositionSpec.RawRes): String =
    resources.getIdentifier(rawRes.name, "raw", packageName).let { resId ->
        resources.openRawResource(resId).use { inputStream ->
            inputStream.bufferedReader().use(BufferedReader::readText)
        }
    }
