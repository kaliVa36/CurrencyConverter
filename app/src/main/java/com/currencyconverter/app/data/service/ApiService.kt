package com.currencyconverter.app.data.service

import com.currencyconverter.app.data.model.CurrenciesModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET
    suspend fun getExchangeRates(): String

    @GET("codes")
    suspend fun getCurrencies(): Response<CurrenciesModel>
}
