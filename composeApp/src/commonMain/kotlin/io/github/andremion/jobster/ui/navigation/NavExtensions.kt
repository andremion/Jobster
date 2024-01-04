package io.github.andremion.jobster.ui.navigation

import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo

//fun Navigator.contains(route: String): Boolean =
//    hierarchy.any { destination ->
//        destination.route?.contains(route) == true
//    }

fun Navigator.navigateSingleTopTo(route: String) {
    navigate(
        route,
        NavOptions(
            launchSingleTop = true,
            popUpTo = PopUpTo.First(inclusive = true)
        )
    ) /*{
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }*/
}

expect fun navigateToUrl(url: String)

expect fun encodeUrl(url: String): String

expect fun decodeUrl(url: String): String
