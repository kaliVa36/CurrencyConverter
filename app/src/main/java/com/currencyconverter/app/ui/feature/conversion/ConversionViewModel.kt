package com.currencyconverter.app.ui.feature.conversion

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyconverter.app.data.datasource.ApiDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversionViewModel @Inject constructor(private val apiDataSource: ApiDataSource) : ViewModel() {
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

        onConvertClicked()
    }

    fun onConvertClicked() {
        viewModelScope.launch {
            apiDataSource.getExchangeRates(_conversionDataState.value.currencyFrom).fold(
                onSuccess = { data ->
                    val convertedAmount =
                        data.conversionRates.filter { item -> item.key == _conversionDataState.value.currencyTo }
                    val amount = if (convertedAmount.isNotEmpty()) {
                        (convertedAmount.values.first() * _conversionDataState.value.amount.toDouble()).toString()
                    } else {
                        "Not found"
                    }

                    _conversionDataState.update { state ->
                        state.copy(convertedAmount = amount)
                    }
                },
                onFailure = {} // error handling
            )
        }
    }
}