package com.currencyconverter.app.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.currencyconverter.app.ui.feature.DummyScreen
import com.currencyconverter.app.ui.feature.conversion.ConversionScreen
import com.currencyconverter.app.ui.feature.conversion.ConversionViewModel
import com.currencyconverter.app.ui.feature.selection.SelectionAndInputScreen
import com.currencyconverter.app.ui.feature.selection.SelectionAndInputViewModel

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
            val viewModel = hiltViewModel<ConversionViewModel>()
            val data by viewModel.conversionDataState.collectAsState()

            LaunchedEffect(null) {
                viewModel.initial(
                    amount = backStack.arguments?.getString(Params.AMOUNT).orEmpty(),
                    currencyFrom = backStack.arguments?.getString(Params.CURRENCY_FROM).orEmpty(),
                    currencyTo = backStack.arguments?.getString(Params.CURRENCY_TO).orEmpty(),
                )
            }

            ConversionScreen(
                data = data,
                onValueChanged = viewModel::onAmountValueChanged,
                onConvertClicked = {},
                onSwitchClicked = viewModel::onSwitchClicked,
            )
        }
    }
}
