package com.zeeshan.gitapp.paging_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.zeeshan.gitapp.common.UIState
import com.zeeshan.gitapp.data.data_source.remote.GitRepoServiceApi
import com.zeeshan.gitapp.data.repository.GitRepositoryImpl
import com.zeeshan.gitapp.domain.model.PullRequest
import com.zeeshan.gitapp.domain.use_cases.GetClosedPrUseCase
import com.zeeshan.gitapp.domain.use_cases.PrUseCase
import com.zeeshan.gitapp.presentation.viewmodels.PullRequestViewModel
import com.zeeshan.gitapp.utils.MainCoroutineScopeRule
import com.zeeshan.gitapp.utils.getValueForTest
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class PullRequestRemotePagingShould {

    @get:Rule
    var mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val server = MockWebServer()
    private lateinit var repository: GitRepoServiceApi
    private lateinit var prUseCase: PrUseCase
    private lateinit var viewModel: PullRequestViewModel

    @Before
    fun init() {
        server.start()
        var BASE_URL = server.url("/").toString()
        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

        repository = service.create(GitRepoServiceApi::class.java)

        prUseCase = PrUseCase(GetClosedPrUseCase(GitRepositoryImpl(repository)))

        viewModel = PullRequestViewModel(prUseCase)

    }

    @Mock
    private lateinit var observer: Observer<in UIState<PagingData<PullRequest>>>

        private val postFactory = PostFactory()
        private val mockPosts = listOf(
            postFactory.createRedditPost(DEFAULT_SUBREDDIT),
            postFactory.createRedditPost(DEFAULT_SUBREDDIT),
            postFactory.createRedditPost(DEFAULT_SUBREDDIT)
        )
        private val mockApi = MockRedditApi().apply {
            mockPosts.forEach { post -> addPost(post) }
        }

        @Test
        fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {
            val pagingSource = ItemKeyedSubredditPagingSource(mockApi, DEFAULT_SUBREDDIT)
        }

}