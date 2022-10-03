package com.vishnusreddy.gpulls_android.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vishnusreddy.gpulls_android.data.api.GitHubAPI
import com.vishnusreddy.gpulls_android.data.model.GithubRepository
import retrofit2.HttpException
import java.io.IOException

class GitHubRepoPagingSource(private val gitHubAPI: GitHubAPI, private val userName: String) :
    PagingSource<Int, GithubRepository>() {

    private val DEFAULT_PAGE_INDEX = 1

    override fun getRefreshKey(state: PagingState<Int, GithubRepository>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepository> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = gitHubAPI.getRepositories(userName, page, params.loadSize)
            LoadResult.Page(
                response, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}