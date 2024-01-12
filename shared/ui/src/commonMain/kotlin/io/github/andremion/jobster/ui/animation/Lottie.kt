package io.github.andremion.jobster.ui.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.alexzhirkevich.compottie.LottieComposition
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import kotlin.jvm.JvmInline

sealed interface LottieCompositionSpec {

    /**
     * Load an animation from resources.
     */
    @JvmInline
    value class AnimationRes(val name: String) : LottieCompositionSpec
}

@Composable
fun rememberLottieComposition(spec: LottieCompositionSpec): State<LottieComposition?> =
    when (val data = rememberLottieData(spec)) {
        null -> mutableStateOf(null)
        else -> rememberLottieComposition(data)
    }

@Composable
fun rememberLottieData(spec: LottieCompositionSpec): String? {
    var data by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(spec) {
        data = when (spec) {
            is LottieCompositionSpec.AnimationRes -> {
                loadAnimationData(spec)
            }
        }
    }
    return data
}

@OptIn(ExperimentalResourceApi::class)
private suspend fun loadAnimationData(animationRes: LottieCompositionSpec.AnimationRes): String =
    resource("animations/${animationRes.name}.json")
        .readBytes().decodeToString()
