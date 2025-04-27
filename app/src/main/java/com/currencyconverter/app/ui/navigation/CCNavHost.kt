package com.currencyconverter.app.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.currencyconverter.app.ui.feature.DummyScreen
import com.currencyconverter.app.ui.feature.SelectionAndInputScreen
import com.currencyconverter.app.ui.feature.SelectionAndInputViewModel

@Composable
fun CCNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.FIRST_SCREEN,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(Route.FIRST_SCREEN) {
            val viewModel = hiltViewModel<SelectionAndInputViewModel>()
            val data by viewModel.selectionAndInputDataState.collectAsState()
            SelectionAndInputScreen(
                data = data,
                onValueChanged = viewModel::onAmountValueChanged,
                onContinueClicked = { navController.navigate(Route.SECOND_SCREEN) },
                onItemFromCurrencySelected = viewModel::onDropdownItemFromCurrencySelected,
                onItemToCurrencySelected = viewModel::onDropdownItemToCurrencySelected,
            )
        }

        composable(Route.SECOND_SCREEN) {
            DummyScreen(Color.Blue) { navController.navigate(Route.FIRST_SCREEN) }
        }
    }
}
