package com.example.common.network

import retrofit2.Response
import java.io.IOException

sealed class ApiResult<T>(
    data: T? = null,
    exception: Throwable? = null
) {
    data class Success<T>(val data: T) : ApiResult<T>(data, null)
    data class Error<T>(val error: Throwable) : ApiResult<T>(null, error)
}

suspend fun <T> makeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
    return try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) ApiResult.Success(data = body)
            else ApiResult.Error(error = Throwable("Response body is null!"))
        } else {
            ApiResult.Error(error = Throwable("Response code: ${response.code()} Response Body: ${response.errorBody()?.string()}"))
        }
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> ApiResult.Error(Throwable("makeApiCall IOException"))
            else -> ApiResult.Error(throwable)
        }
    }
}