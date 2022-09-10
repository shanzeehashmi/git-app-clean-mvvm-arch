package com.zeeshan.gitapp.data.data_source.remote.retrofit.adapters.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zeeshan.gitapp.common.NetworkResult
import com.zeeshan.gitapp.common.PullRequestType
import com.zeeshan.gitapp.data.data_source.remote.GitRepoServiceApi
import com.zeeshan.gitapp.domain.model.PullRequest

class PullRequestPagingSource(private val apiService: GitRepoServiceApi) :
    PagingSource<Int, PullRequest>() {

    override fun getRefreshKey(state: PagingState<Int, PullRequest>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PullRequest> {

        val pageNumber = params.key ?: Constants.DEFAULT_PAGE
        val response = apiService.fetchPullRequest(
            PullRequestType.CLOSED.name.lowercase(),
            Constants.ITEM_PER_PAGE,
            pageNumber = pageNumber
        )

        return when (response) {
            is NetworkResult.Success -> {

                val nextKey = if (response.data.isEmpty()) {
                    null
                } else {
                    pageNumber + (params.loadSize / Constants.ITEM_PER_PAGE)
                }

                LoadResult.Page(
                    data = response.data,
                    prevKey = null, // Only paging forward.
                    nextKey = nextKey
                )
            }

            is NetworkResult.Error -> {
                LoadResult.Error(Throwable(response.errorMessage))
            }

            is NetworkResult.Exception -> {
                LoadResult.Error(response.error)
            }

            else -> {
                LoadResult.Error(throwable = Throwable("paging-unknown-error"))
            }
        }


    }


}