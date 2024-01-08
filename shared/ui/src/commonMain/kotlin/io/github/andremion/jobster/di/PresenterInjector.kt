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
import kotlin.reflect.KClass

@Composable
// Cannot use reified duo to the iOS compilation, so I had to introduce the clazz parameter
// https://github.com/JetBrains/compose-multiplatform/issues/2900
inline fun <T : Presenter<*, *, *>> injectPresenter(
    clazz: KClass<T>,
    key: String? = null,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
    stateHolder: StateHolder = LocalStateHolder.current,
    scope: Scope = LocalKoinScope.current,
): T = koinInject(clazz, key, qualifier, parameters, stateHolder, scope).apply {
    presenterScope = rememberCoroutineScope()
}

@Composable
// Cannot use reified duo to the iOS compilation, so I had to introduce the clazz parameter
// https://github.com/JetBrains/compose-multiplatform/issues/2900
inline fun <T : Any> koinInject(
    clazz: KClass<T>,
    key: String? = null,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
    stateHolder: StateHolder = LocalStateHolder.current,
    scope: Scope = LocalKoinScope.current,
): T = stateHolder.getOrPut(qualifier?.value ?: key ?: clazz.qualifiedName ?: "") {
    scope.get(clazz, qualifier, parameters)
}
