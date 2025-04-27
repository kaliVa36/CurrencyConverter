package com.currencyconverter.app

import com.currencyconverter.app.data.datasource.ApiDataSourceImpl
import com.currencyconverter.app.data.model.CurrenciesModel
import com.currencyconverter.app.data.model.CurrencyRatesModel
import com.currencyconverter.app.data.service.ApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class ApiDataSourceTest {
    private lateinit var repository: ApiDataSourceImpl
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = mock(ApiService::class.java)
        repository = ApiDataSourceImpl(apiService)
    }

    @Test
    fun `getExchangeRates returns success when API call is successful`() = runBlocking {
        val currency = "USD"
        val fakeResponse = CurrencyRatesModel(
            result = "success",
            documentation = "https://www.exchangerate-api.com/docs",
            termsOfUse = "https://www.exchangerate-api.com/terms",
            timeLastUpdateUnix = 1745712001L,
            timeLastUpdateUtc = "Sun, 27 Apr 2025 00:00:01 +0000",
            timeNextUpdateUnix = 1745798401L,
            timeNextUpdateUtc = "Mon, 28 Apr 2025 00:00:01 +0000",
            baseCode = "USD",
            conversionRates = mapOf(
                "USD" to 1.0,
                "EUR" to 0.88,
                "GBP" to 0.75,
                "JPY" to 143.60
            )
        )

        `when`(apiService.getExchangeRates(currency)).thenReturn(Response.success(fakeResponse))

        val result = repository.getExchangeRates(currency)

        assertTrue(result.isSuccess)
        assertEquals(fakeResponse, result.getOrNull())
    }

    @Test
    fun `getCurrencies returns success when API call is successful`() = runBlocking {
        val fakeResponse = CurrenciesModel(
            result = "success",
            documentation = "https://www.exchangerate-api.com/docs",
            supportedCodes = listOf(
                listOf("USD", "United States Dollar"),
                listOf("EUR", "Euro"),
                listOf("GBP", "British Pound Sterling"),
                listOf("JPY", "Japanese Yen"),
                listOf("BGN", "Bulgarian Lev")
            )
        )

        `when`(apiService.getCurrencies()).thenReturn(Response.success(fakeResponse))

        val result = repository.getCurrencies()

        assertTrue(result.isSuccess)
        assertEquals(fakeResponse, result.getOrNull())
    }

    @Test
    fun `getExchangeRates returns failure when API call fails`() = runBlocking {
        val currency = "USD"
        `when`(apiService.getExchangeRates(currency)).thenReturn(Response.error(400, okhttp3.ResponseBody.create(null, "")))

        val result = repository.getExchangeRates(currency)

        assertTrue(result.isFailure)
    }

    @Test
    fun `getCurrencies returns failure when API call fails`() = runBlocking {
        `when`(apiService.getCurrencies()).thenReturn(Response.error(400, okhttp3.ResponseBody.create(null, "")))

        val result = repository.getCurrencies()

        assertTrue(result.isFailure)
    }
}
