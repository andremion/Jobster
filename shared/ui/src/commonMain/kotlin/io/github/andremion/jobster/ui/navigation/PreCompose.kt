package io.github.andremion.jobster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.stateholder.LocalStateHolder

@Composable
fun rememberNavigator(name: String): Navigator =
    LocalStateHolder.current.let { stateHolder ->
        remember { stateHolder.getOrPut("${name}Navigator") { Navigator() } }
    }
