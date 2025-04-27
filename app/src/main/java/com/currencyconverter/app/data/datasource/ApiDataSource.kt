package com.currencyconverter.app.data.datasource

import com.currencyconverter.app.data.model.CurrenciesModel

interface ApiDataSource {
    suspend fun getExchangeRates(): String
    suspend fun getCurrencies(): Result<CurrenciesModel>
}
