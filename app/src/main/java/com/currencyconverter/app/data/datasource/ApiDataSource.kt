package com.currencyconverter.app.data.datasource

import com.currencyconverter.app.data.model.CurrenciesModel
import com.currencyconverter.app.data.model.CurrencyRatesModel

interface ApiDataSource {
    suspend fun getExchangeRates(currency: String): Result<CurrencyRatesModel>
    suspend fun getCurrencies(): Result<CurrenciesModel>
}
