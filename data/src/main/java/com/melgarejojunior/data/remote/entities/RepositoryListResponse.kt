package com.melgarejojunior.data.remote.entities

import com.google.gson.annotations.SerializedName

internal data class RepositoryListResponse(
    @SerializedName("total_count") val totalCount: Int? = null,
    @SerializedName("incomplete_results") val incompleteResults: Boolean? = null,
    @SerializedName("items") val items: List<RepositoryResponse>? = null,
)
