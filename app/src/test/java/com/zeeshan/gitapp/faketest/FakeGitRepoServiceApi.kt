package com.zeeshan.gitapp.faketest

import com.zeeshan.gitapp.common.NetworkResult
import com.zeeshan.gitapp.data.data_source.remote.GitRepoServiceApi
import com.zeeshan.gitapp.domain.model.PullRequest
import com.zeeshan.gitapp.domain.model.UserProfile
import com.zeeshan.gitapp.paging_test.PullRequestFactory

class FakeGitApi : GitRepoServiceApi {

    private val mockPullRequestList = PullRequestFactory.mockPullRequestList
    override suspend fun fetchPullRequest(
        state: String,
        perPage: Int,
        pageNumber: Int
    ): NetworkResult<List<PullRequest>> {
        return NetworkResult.Success(mockPullRequestList)
    }
}