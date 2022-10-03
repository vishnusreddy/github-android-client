package com.vishnusreddy.gpulls_android.data.api

import com.vishnusreddy.gpulls_android.data.model.GithubPullRequest
import com.vishnusreddy.gpulls_android.data.model.GithubRepository
import com.vishnusreddy.gpulls_android.data.model.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubAPI {
    // GET Request for GitHub user from UserName
    @GET("users/{userName}")
    suspend fun getUserInfo(
        @Path("userName") userName: String,
    ): Response<GithubUser>

    // GET Request for List of Repositories with Support for Pagination
    @GET("users/{userName}/repos")
    suspend fun getRepositories(
        @Path("userName") userName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<GithubRepository>

    // GET Request for List of Closed PRs for a Given Repository with Support for Pagination
    @GET("repos/{userName}/{repository}/pulls")
    suspend fun getClosedPRs(
        @Path("userName") userName: String,
        @Path("repository") repository: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("state") state: String
    ): List<GithubPullRequest>

}