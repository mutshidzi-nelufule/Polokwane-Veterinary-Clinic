package com.example.polokwanevetclinic.data.remote

import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(
    call: suspend () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(call())
    } catch (e: IOException) {
        ApiResult.Error("Network connection failed. Check your connection.")
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "An unexpected error occurred.")
    }
}

suspend fun <T> safeApiResponse(
    call: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = call()

        if (response.isSuccessful) {
            val body = response.body()

            if (body != null) {
                ApiResult.Success(body)
            } else {
                ApiResult.Error("The server returned an empty response.")
            }
        } else {
            ApiResult.Error(
                message = "Request failed: ${response.code()}",
                code = response.code()
            )
        }
    } catch (e: IOException) {
        ApiResult.Error("Network connection failed. Check your connection.")
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "An unexpected error occurred.")
    }
}
