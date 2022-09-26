package com.vishnusreddy.gpulls_android.data.model

import com.google.gson.annotations.SerializedName

/**
 * Using GitHub API docs to build this Repository model. Many other parameters are
 * available. Please add them as per your requirements. For this project this seems sufficient.
 * @see <a href="https://docs.github.com/en/rest/repos/repos#list-repositories-for-a-user">GitHub API Docs</a>
 * @author Vishnu S Reddy*/
data class GithubRepository(
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String?,
    @SerializedName("language") var language: String?,
    @SerializedName("stargazers_count") var startCount: Int?,
    @SerializedName("watchers_count") var watchersCount: Int?,
    @SerializedName("open_issues_count") var openIssuesCount: Int?,
)