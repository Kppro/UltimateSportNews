package com.usn.ultimatesportnews.data.repository

import com.usn.ultimatesportnews.data.local.DatabaseUsn
import com.usn.ultimatesportnews.data.network.ApiUsn

object RepositoryManager {

    val newsRepository = NewsDataRepository(
        newsApi = ApiUsn.get(),
        newsDao = DatabaseUsn.get().newsDao()
    )

}