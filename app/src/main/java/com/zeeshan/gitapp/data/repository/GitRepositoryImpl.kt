package com.zeeshan.gitapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zeeshan.gitapp.common.NetworkResult
import com.zeeshan.gitapp.common.PullRequestType
import com.zeeshan.gitapp.data.data_source.remote.GitRepoServiceApi
import com.zeeshan.gitapp.data.data_source.remote.retrofit.adapters.paging.PullRequestPagingSource
import com.zeeshan.gitapp.domain.model.PullRequest
import com.zeeshan.gitapp.domain.repository.GitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitRepositoryImpl @Inject constructor(private val api: GitRepoServiceApi) : GitRepository {

    override suspend fun getPaginatedClosedPullRequestRemote(): Flow<PagingData<PullRequest>> {

            return Pager(
                config = PagingConfig(
                    pageSize = 5,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { PullRequestPagingSource(api) }
            ).flow

    }

}