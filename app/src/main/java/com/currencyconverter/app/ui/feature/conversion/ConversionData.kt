package com.currencyconverter.app.ui.feature.conversion

data class ConversionData(
    val amount: String = "",
    val currencyFrom: String = "",
    val currencyTo: String = "",
    val convertedAmount: String = "",
    val error: String = "",
)
