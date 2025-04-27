package com.currencyconverter.app.data.datasource

import com.currencyconverter.app.data.model.CurrenciesModel
import com.currencyconverter.app.data.model.CurrencyRatesModel
import com.currencyconverter.app.data.requestBody
import com.currencyconverter.app.data.service.ApiService

class ApiDataSourceImpl(private val apiService: ApiService): ApiDataSource {
    override suspend fun getExchangeRates(currency: String): Result<CurrencyRatesModel> {
        return requestBody(apiService.getExchangeRates(currency))
    }

    override suspend fun getCurrencies(): Result<CurrenciesModel> {
        return requestBody(apiService.getCurrencies())
    }
}
