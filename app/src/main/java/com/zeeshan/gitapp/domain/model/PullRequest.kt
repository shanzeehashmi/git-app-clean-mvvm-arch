package com.zeeshan.gitapp.domain.model

import com.google.gson.annotations.SerializedName

data class PullRequest(
    @SerializedName("id")
    val id: Int,
    @SerializedName("number")
    val prNumber: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("created_at")
    val createdDate: String,
    @SerializedName("closed_at")
    val closedDate: String,
    @SerializedName("user")
    val userProfile: UserProfile
)

data class UserProfile(
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val profilePicUrl: String,
)
