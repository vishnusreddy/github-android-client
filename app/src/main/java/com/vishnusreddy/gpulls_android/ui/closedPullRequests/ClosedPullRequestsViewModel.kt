package com.vishnusreddy.gpulls_android.ui.closedPullRequests

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vishnusreddy.gpulls_android.data.model.GithubPullRequest
import com.vishnusreddy.gpulls_android.data.model.GithubRepository
import com.vishnusreddy.gpulls_android.data.repository.AppNetworkRepository
import com.vishnusreddy.gpulls_android.utils.network.NetworkTools

class ClosedPullRequestsViewModel : ViewModel() {
    private val repository: AppNetworkRepository = AppNetworkRepository(NetworkTools.githubAPI)

    fun fetchClosedReposLiveData(userName: String, repoName: String): LiveData<PagingData<GithubPullRequest>> {
        return repository.getClosedPullRequestsLiveData(userName, repoName)
            .map { pagingData -> pagingData.map { it } }
            .cachedIn(viewModelScope)
    }
}