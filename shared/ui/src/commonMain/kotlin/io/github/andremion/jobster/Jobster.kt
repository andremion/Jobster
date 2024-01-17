package io.github.andremion.jobster

import androidx.compose.runtime.Composable
import io.github.andremion.jobster.ui.navigation.MainNavHost
import io.github.andremion.jobster.ui.theme.AppTheme
import moe.tlaster.precompose.PreComposeApp

@Composable
fun Jobster() {
    PreComposeApp {
        AppTheme {
            MainNavHost()
        }
    }
}
