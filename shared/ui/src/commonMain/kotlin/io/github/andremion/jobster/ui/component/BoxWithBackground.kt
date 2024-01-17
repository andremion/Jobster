package io.github.andremion.jobster.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BoxWithBackground(
    modifier: Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier,
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource("images/bg_gemini.png"),
            contentDescription = "Background"
        )
        content(this)
    }
}
