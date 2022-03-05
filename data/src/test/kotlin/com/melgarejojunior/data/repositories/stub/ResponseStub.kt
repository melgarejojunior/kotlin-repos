package com.melgarejojunior.data.repositories.stub

const val STUB_ALIAS = "123"
const val STUB_ORIGINAL_URL = "https://google.com"
const val STUB_SHORTENED_URL = "https://short-url.com/${STUB_ALIAS}"

val getShortenedUrlSuccessfulResponse = """
    {
    "alias":"$STUB_ALIAS",
    "_links": {
    "self":"$STUB_ORIGINAL_URL",
    "short":"$STUB_SHORTENED_URL"
    }
    }
""".trimIndent()