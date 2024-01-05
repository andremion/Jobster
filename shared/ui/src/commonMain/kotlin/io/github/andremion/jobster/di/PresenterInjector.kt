package io.github.andremion.jobster.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import io.github.andremion.boomerang.Presenter
import moe.tlaster.precompose.stateholder.LocalStateHolder
import moe.tlaster.precompose.stateholder.StateHolder
import org.koin.compose.LocalKoinScope
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

@Composable
inline fun <reified T : Presenter<*, *, *>> injectPresenter(
    key: String? = null,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
    stateHolder: StateHolder = LocalStateHolder.current,
    scope: Scope = LocalKoinScope.current,
): T = koinInject<T>(key, qualifier, parameters, stateHolder, scope).apply {
    presenterScope = rememberCoroutineScope()
}

@Composable
inline fun <reified T : Any> koinInject(
    key: String? = null,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
    stateHolder: StateHolder = LocalStateHolder.current,
    scope: Scope = LocalKoinScope.current,
): T {
    val clazz = T::class
    return stateHolder.getOrPut(qualifier?.value ?: key ?: clazz.qualifiedName ?: "") {
        scope.get(clazz, qualifier, parameters)
    }
}
