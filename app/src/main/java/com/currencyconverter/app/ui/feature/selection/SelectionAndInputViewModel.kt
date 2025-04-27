package com.currencyconverter.app.ui.feature.selection

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
class SelectionAndInputViewModel @Inject constructor(private val apiDataSource: ApiDataSource) : ViewModel() {
    private val _selectionAndInputDataState = MutableStateFlow(SelectionAndInputData())
    val selectionAndInputDataState = _selectionAndInputDataState.asStateFlow()

    init {
        viewModelScope.launch {
            apiDataSource.getCurrencies().fold(
                onSuccess = { data ->
                    val arrayList = arrayListOf<String>()
                    val mappedCurrencies = data.supportedCodes.map { items -> items[0] }
                    arrayList.addAll(mappedCurrencies)

                    _selectionAndInputDataState.update { state ->
                        state.copy(
                            currencies = mappedCurrencies,
                            currenciesNotSelected = arrayList,
                            selectedFromCurrency = mappedCurrencies[0],
                            selectedToCurrency = mappedCurrencies[1],
                        )
                    }
                },
                onFailure = { error ->
                    _selectionAndInputDataState.update { state ->
                        state.copy(error = error.message.toString())
                    }
                }
            )
        }
    }

    fun resetError() {
        _selectionAndInputDataState.update { state ->
            state.copy(error = "")
        }
    }

    fun onAmountValueChanged(value: String) {
        if (value.isDigitsOnly()) {
            _selectionAndInputDataState.update { state ->
                state.copy(amount = value)
            }
        }
    }

    fun onDropdownItemFromCurrencySelected(value: String) {
        _selectionAndInputDataState.update { state ->
            state.copy(selectedFromCurrency = value)
        }
    }

    fun onDropdownItemToCurrencySelected(value: String) {
        _selectionAndInputDataState.update { state ->
            state.copy(selectedToCurrency = value)
        }
    }
}