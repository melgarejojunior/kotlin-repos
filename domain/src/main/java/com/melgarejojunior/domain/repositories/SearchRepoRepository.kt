package com.melgarejojunior.domain.repositories

import com.melgarejojunior.domain.entities.Language
import com.melgarejojunior.domain.entities.SortBy

interface SearchRepoRepository {
    fun getRepositories(language: Language, sortBy: SortBy, page: Int)
}