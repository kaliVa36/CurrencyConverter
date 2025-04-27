package com.currencyconverter.app.ui.feature.conversion

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConversionViewModel @Inject constructor() : ViewModel() {
    private val _conversionDataState = MutableStateFlow(ConversionData())
    val conversionDataState = _conversionDataState.asStateFlow()

    fun initial(amount: String, currencyFrom: String, currencyTo: String) {
        _conversionDataState.update { state ->
            state.copy(
                amount = amount,
                currencyFrom = currencyFrom,
                currencyTo = currencyTo,
            )
        }
    }

    fun onAmountValueChanged(value: String) {
        if (value.isDigitsOnly()) {
            _conversionDataState.update { state ->
                state.copy(amount = value)
            }
        }
    }

    fun onSwitchClicked() {
        val currencyFrom = _conversionDataState.value.currencyFrom
        val currencyTo = _conversionDataState.value.currencyTo
        _conversionDataState.update { state ->
            state.copy(
                currencyFrom = currencyTo,
                currencyTo = currencyFrom,
            )
        }
    }
}