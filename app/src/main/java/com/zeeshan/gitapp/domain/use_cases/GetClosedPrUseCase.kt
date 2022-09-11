package com.zeeshan.gitapp.domain.use_cases

import androidx.paging.PagingData
import com.zeeshan.gitapp.domain.model.PullRequest
import com.zeeshan.gitapp.domain.repository.GitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClosedPrUseCase @Inject constructor(private val repository: GitRepository) {
    suspend operator fun invoke(): Flow<PagingData<PullRequest>> =
        repository.getPaginatedClosedPullRequestRemote()
}