package com.zeeshan.gitapp.viewmodel_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zeeshan.gitapp.common.UIState
import com.zeeshan.gitapp.data.repository.GitRepositoryImpl
import com.zeeshan.gitapp.domain.use_cases.GetClosedPrUseCase
import com.zeeshan.gitapp.domain.use_cases.PrUseCase
import com.zeeshan.gitapp.faketest.FakeGitRepoServiceApi
import com.zeeshan.gitapp.presentation.viewmodels.PullRequestViewModel
import com.zeeshan.gitapp.utils.MainCoroutineScopeRule
import com.zeeshan.gitapp.utils.getValueForTest
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PullRequestViewModelShould {

    @get:Rule
    var mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var prUseCase: PrUseCase
    private lateinit var viewModel: PullRequestViewModel


    @Before
    fun init() {

        prUseCase = PrUseCase(GetClosedPrUseCase(GitRepositoryImpl(FakeGitRepoServiceApi())))
        viewModel = PullRequestViewModel(prUseCase)

    }


    @Test
    fun getSuccessStateFromClosedPulledRequestFlow() {
        viewModel.getClosedPullRequestFlow()
        runBlocking {
            delay(2500)
            assertTrue(viewModel.openPullRequest.getValueForTest() is (UIState.Success))
        }
    }

    @Test
    fun getLoadingStateWhenRequestingFlow(){
        viewModel.getClosedPullRequestFlow()
        assertTrue(viewModel.openPullRequest.getValueForTest() is (UIState.Loading))
    }


}