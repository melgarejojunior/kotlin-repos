package com.melgarejojunior.domain.entities

data class Owner(
    val login: String,
    val id: Long? = null,
    val nodeId: String? = null,
    val avatarUrl: String? = null,
) {
    companion object {
        fun empty() = Owner(login = "")
    }
}
