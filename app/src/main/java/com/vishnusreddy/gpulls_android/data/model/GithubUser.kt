package com.vishnusreddy.gpulls_android.data.model

import com.google.gson.annotations.SerializedName

/**
 * Using GitHub API docs to build this User model. Many other parameters are
 * available. Please add them as per your requirements. For this project this seems sufficient.
 * @see <a href="https://docs.github.com/en/rest/users/users#get-a-user">GitHub API Docs</a>
 * @author Vishnu S Reddy*/
data class GithubUser(
    @SerializedName("login") val userName: String?,
    @SerializedName("avatar_url") val displayPicture: String?,
    @SerializedName("followers") val followersCount: Int,
    @SerializedName("public_repos") val publicRepoCount: Int,
)