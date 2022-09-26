package com.vishnusreddy.gpulls_android.data.model

import com.google.gson.annotations.SerializedName

data class GithubPullRequest(
    @SerializedName("title") var title: String?,
    @SerializedName("created_at") var createdDate: String?,
    @SerializedName("closed_at") var closedDate: String?,
    @SerializedName("user") var user: GithubUser?,
    @SerializedName("number") var pullRequestId: Int?,
)
