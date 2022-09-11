package com.zeeshan.gitapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zeeshan.gitapp.data.data_source.remote.GitRepoServiceApi
import com.zeeshan.gitapp.data.data_source.remote.network.adapters.paging.Constants
import com.zeeshan.gitapp.data.data_source.remote.network.adapters.paging.PullRequestPagingSource
import com.zeeshan.gitapp.domain.model.PullRequest
import com.zeeshan.gitapp.domain.repository.GitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitRepositoryImpl @Inject constructor(private val api: GitRepoServiceApi) : GitRepository {

    override suspend fun getPaginatedClosedPullRequestRemote(): Flow<PagingData<PullRequest>> {

            return Pager(
                config = PagingConfig(
                    pageSize = Constants.ITEM_PER_PAGE,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { PullRequestPagingSource(api) }
            ).flow

    }

}