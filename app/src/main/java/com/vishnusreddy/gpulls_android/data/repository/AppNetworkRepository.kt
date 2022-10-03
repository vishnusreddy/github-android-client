package com.vishnusreddy.gpulls_android.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.vishnusreddy.gpulls_android.data.api.GitHubAPI
import com.vishnusreddy.gpulls_android.data.model.GithubPullRequest
import com.vishnusreddy.gpulls_android.data.model.GithubRepository
import com.vishnusreddy.gpulls_android.data.model.GithubUser
import com.vishnusreddy.gpulls_android.utils.PagingUtils.getDefaultPageConfig
import retrofit2.Response


class AppNetworkRepository constructor(private val networkApi: GitHubAPI) {

    suspend fun getUserInfo(userName: String): Response<GithubUser> {
        return networkApi.getUserInfo(userName)
    }

    fun getRepositoriesLiveData(
        userName: String,
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): LiveData<PagingData<GithubRepository>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { GitHubRepoPagingSource(networkApi, userName) }
        ).liveData
    }

    fun getClosedPullRequestsLiveData(
        userName: String,
        repo: String,
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): LiveData<PagingData<GithubPullRequest>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                GitHubClosedPullRequestsPagingSource(
                    networkApi,
                    userName,
                    repo
                )
            }
        ).liveData
    }


}