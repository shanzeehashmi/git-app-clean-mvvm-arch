package com.zeeshan.gitapp.paging_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.zeeshan.gitapp.data.data_source.remote.network.paging_source.PullRequestRemotePagingSource
import com.zeeshan.gitapp.domain.model.PullRequest
import com.zeeshan.gitapp.faketest.FakeGitRepoServiceApi
import com.zeeshan.gitapp.utils.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class PullRequestRemotePagingShould {

    @get:Rule
    var mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockPullRequestList: List<PullRequest>

    @Before
    fun init() {

        mockPullRequestList = PullRequestFactory.mockPullRequestList

    }

    @Test
    fun loadPaginatedPageSuccessfully() = runTest {
        val pagingSource = PullRequestRemotePagingSource(FakeGitRepoServiceApi())

        val expected = PagingSource.LoadResult.Page(
            data = mockPullRequestList,
            prevKey = null,
            nextKey = 2
        )

        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 5,
                placeholdersEnabled = false
            )
        )

        Assert.assertEquals(expected, actual)
    }

}