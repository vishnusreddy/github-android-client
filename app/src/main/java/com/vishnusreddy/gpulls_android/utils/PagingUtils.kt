package com.vishnusreddy.gpulls_android.utils

import androidx.paging.PagingConfig

object PagingUtils {
    val DEFAULT_PAGE_SIZE = 20

    fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }
}