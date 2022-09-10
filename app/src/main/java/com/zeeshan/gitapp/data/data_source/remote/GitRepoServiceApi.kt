package com.zeeshan.gitapp.data.data_source.remote

import com.zeeshan.gitapp.common.NetworkResult
import com.zeeshan.gitapp.domain.model.PullRequest
import retrofit2.http.GET
import retrofit2.http.Query

interface GitRepoServiceApi {

    @GET("/repos/square/retrofit/pulls")
    suspend fun fetchPullRequest(
        @Query("state") state: String,
        @Query("per_page") perPage: Int,
        @Query("page") pageNumber: Int
    ): NetworkResult<List<PullRequest>>

}