package io.github.andremion.jobster.ui.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import io.github.alexzhirkevich.compottie.LottieComposition
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import kotlin.jvm.JvmInline

sealed interface LottieCompositionSpec {

    /**
     * Load an animation from res/raw.
     */
    @JvmInline
    value class RawRes(val name: String) : LottieCompositionSpec
}

@Composable
fun rememberLottieComposition(spec: LottieCompositionSpec): State<LottieComposition?> =
    rememberLottieComposition(rememberLottieData(spec))

@Composable
expect fun rememberLottieData(
    spec: LottieCompositionSpec,
): String
