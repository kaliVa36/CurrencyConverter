package com.currencyconverter.app.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.currencyconverter.app.ui.feature.DummyScreen
import com.currencyconverter.app.ui.feature.DummyViewModel

@Composable
fun CCNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.SPLASH_SCREEN,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(Route.SPLASH_SCREEN) {
            val viewModel = hiltViewModel<DummyViewModel>()
            DummyScreen(Color.Red) { navController.navigate(Route.HOME_SCREEN) }
        }

        composable(Route.HOME_SCREEN) {
            DummyScreen(Color.Blue) { navController.navigate(Route.SPLASH_SCREEN) }
        }
    }
}
