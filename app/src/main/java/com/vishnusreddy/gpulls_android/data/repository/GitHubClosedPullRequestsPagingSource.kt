package com.vishnusreddy.gpulls_android.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vishnusreddy.gpulls_android.data.api.GitHubAPI
import com.vishnusreddy.gpulls_android.data.model.GithubPullRequest
import retrofit2.HttpException
import java.io.IOException

class GitHubClosedPullRequestsPagingSource(
    private val gitHubAPI: GitHubAPI,
    private val userName: String,
    private val repoName: String):
    PagingSource<Int, GithubPullRequest>() {

    private val DEFAULT_PAGE_INDEX = 1
    private val STATE_CLOSED = "closed"

    override fun getRefreshKey(state: PagingState<Int, GithubPullRequest>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubPullRequest> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = gitHubAPI.getClosedPRs(userName, repoName, page, params.loadSize, STATE_CLOSED)
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