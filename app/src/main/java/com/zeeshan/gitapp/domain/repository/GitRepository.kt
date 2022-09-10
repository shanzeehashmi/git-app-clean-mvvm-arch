package com.zeeshan.gitapp.domain.repository

import androidx.paging.PagingData
import com.zeeshan.gitapp.domain.model.PullRequest
import kotlinx.coroutines.flow.Flow

interface GitRepository {
    suspend fun getPaginatedClosedPullRequestRemote(): Flow<PagingData<PullRequest>>
}