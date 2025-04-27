package com.currencyconverter.app.ui.navigation

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.os.bundleOf
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
        startDestination = NavigationConstants.FIRST_SCREEN,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(NavigationConstants.FIRST_SCREEN) {
            val viewModel = hiltViewModel<SelectionAndInputViewModel>()
            val data by viewModel.selectionAndInputDataState.collectAsState()
            SelectionAndInputScreen(
                data = data,
                onValueChanged = viewModel::onAmountValueChanged,
                onContinueClicked = {
                    navController.navigate(NavigationConstants.SECOND_SCREEN)
                    navController.navigate(
                        NavigationConstants.SECOND_SCREEN, bundleOf(
                            Params.CURRENCY_FROM to data.selectedFromCurrency,
                            Params.CURRENCY_TO to data.selectedToCurrency,
                            Params.AMOUNT to data.amount
                        )
                    )
                },
                onItemFromCurrencySelected = viewModel::onDropdownItemFromCurrencySelected,
                onItemToCurrencySelected = viewModel::onDropdownItemToCurrencySelected,
            )
        }

        composable(NavigationConstants.SECOND_SCREEN) { backStack ->
            // Will be fixed soon
            val currencyFrom = backStack.arguments?.getString(Params.CURRENCY_FROM)
            val currencyTo = backStack.arguments?.getString(Params.CURRENCY_TO)
            val amount = backStack.arguments?.getString(Params.AMOUNT)

            DummyScreen(Color.Blue) { navController.navigate(NavigationConstants.FIRST_SCREEN) }
        }
    }
}
