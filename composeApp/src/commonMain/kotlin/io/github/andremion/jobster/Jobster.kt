package io.github.andremion.jobster

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.andremion.jobster.ui.navigation.MainNavHost
import io.github.andremion.jobster.ui.theme.AppTheme
import moe.tlaster.precompose.PreComposeApp

@Composable
fun Jobster() {
    PreComposeApp {
        AppTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MainNavHost()
            }
        }
    }
}
