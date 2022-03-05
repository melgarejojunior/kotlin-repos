package com.melgarejojunior.data.remote.datasource

import com.melgarejojunior.data.remote.exceptions.HttpException
import com.melgarejojunior.data.remote.exceptions.NullBodyException
import kotlinx.coroutines.coroutineScope
import retrofit2.Response
import java.io.EOFException

suspend fun <T : Any> makeRequest(block: suspend () -> Response<T>): T {
    return coroutineScope {
        try {
            block().run {
                if (isSuccessful) {
                    body() ?: throw NullBodyException()
                } else {
                    throw HttpException(this.code())
                }
            }
        } catch (e: Exception) {
            throw when (e) {
                is EOFException -> NullBodyException()
                else -> e
            }
        }
    }
}