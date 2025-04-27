package com.currencyconverter.app.ui.feature.selection

data class SelectionAndInputData(
    val currencies: List<String> = emptyList(),
    val selectedFromCurrency: String = "",
    val selectedToCurrency: String = "",
    val amount: String = "",
    val currenciesNotSelected: ArrayList<String> = arrayListOf(),
)
