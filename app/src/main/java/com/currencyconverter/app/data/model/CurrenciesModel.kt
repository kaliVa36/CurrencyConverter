package com.currencyconverter.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesModel(
    @SerialName("result")
    val result: String,
    @SerialName("documentation")
    val documentation: String,
    @SerialName("supported_codes")
    val supportedCodes: List<List<String>>
)
