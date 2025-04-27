package com.currencyconverter.app.data

import retrofit2.Response

fun <T> requestBody(request: Response<T>): Result<T> {
    return try {
        if (request.isSuccessful) {
            request.body()?.let {
                Result.success(it)
            } ?: Result.failure(Throwable("Error"))
        } else {
            Result.failure(Throwable("Error"))
        }
    } catch (ex: IllegalArgumentException) {
        Result.failure(Throwable("Error"))
    }
}
