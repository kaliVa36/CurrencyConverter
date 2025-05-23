package com.currencyconverter.app.ui.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.navOptions

@SuppressLint("RestrictedApi")
fun NavHostController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}

fun navigateAndPopBackstack(screen: String, navHostController: NavHostController) {
    navHostController.currentBackStackEntry?.destination?.parent?.id?.let { parentGraph ->
        navHostController.navigate(
            route = screen,
            navOptions = navOptions {
                popUpTo(
                    id = parentGraph,
                    popUpToBuilder = { inclusive = true }
                )
                launchSingleTop = true
            }
        )
    }
}
