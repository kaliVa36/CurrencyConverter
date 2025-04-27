package com.currencyconverter.app.data.service

import com.currencyconverter.app.data.model.CurrenciesModel
import com.currencyconverter.app.data.model.CurrencyRatesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("latest/{currency}")
    suspend fun getExchangeRates(@Path("currency") country: String): Response<CurrencyRatesModel>

    @GET("codes")
    suspend fun getCurrencies(): Response<CurrenciesModel>
}
