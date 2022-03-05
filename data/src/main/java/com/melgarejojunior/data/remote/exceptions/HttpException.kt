package com.melgarejojunior.data.remote.exceptions

data class HttpException(val code: Int, override val cause: Throwable? = null) : Exception(cause)