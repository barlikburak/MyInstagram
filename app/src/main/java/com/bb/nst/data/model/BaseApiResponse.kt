package com.bb.nst.data.model

import com.bb.nst.utils.network.NetworkResult
import retrofit2.Response

abstract class BaseApiResponse {

    /**
     * Is a function that returns Response<T> as a parameter.
     * We call the function with apiCall() and assign it to the response.
     * If the connection is successful and the body is not empty,
     * We return the data we received with Response<T> and the success status with NetworkResult<T>.
     * if there is an error, we also return this status.
     */
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    /**
     * Returns error detail as NetworkResult<T>.
     */
    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}