package com.zeeshan.gitapp.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeeshan.gitapp.common.UIState
import com.zeeshan.gitapp.databinding.ActivityClosedPullRequestBinding
import com.zeeshan.gitapp.presentation.paging.adapter.LoadingStateAdapter
import com.zeeshan.gitapp.presentation.paging.adapter.PagingAdapter
import com.zeeshan.gitapp.presentation.viewmodels.PullRequestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@AndroidEntryPoint
class ClosedPullRequestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClosedPullRequestBinding
    private val viewModel: PullRequestViewModel by viewModels()
    private val pagingAdapter: PagingAdapter by lazy {
        PagingAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityClosedPullRequestBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupPagingRv()
        if (savedInstanceState == null) {
            fetchPullRequests()
        }

        viewModel.openPullRequest.observe(this) {
            lifecycleScope.launch() {
                showLoading(toShow = false)
                when (it) {
                    is UIState.Loading -> {
                        showLoading(toShow = true)
                    }
                    is UIState.Success -> {
                        it.data?.apply {
                            pagingAdapter.submitData(this)
                        }

                    }
                    else -> {}
                }

            }

        }


        binding.retryBtn.setOnClickListener {
            pagingAdapter.retry()
        }
    }

    private fun showLoading(toShow: Boolean) {
        if (toShow) {
            hideErrorHandlingLayout()
            binding.errorHandlingLayout.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun hideErrorHandlingLayout() {
        binding.errorHandlingLayout.visibility = View.GONE
        binding.errorMessageTv.visibility = View.GONE
        binding.errorIcon.visibility = View.GONE
        binding.retryBtn.visibility = View.GONE
        binding.offlineIcon.visibility = View.GONE
    }

    private fun showError(message: String, showRetry: Boolean = true) {
        hideErrorHandlingLayout()
        showLoading(toShow = false)
        binding.errorHandlingLayout.visibility = View.VISIBLE
        binding.errorIcon.visibility = View.VISIBLE
        binding.errorMessageTv.visibility = View.VISIBLE
        binding.errorMessageTv.text = message
        binding.retryBtn.visibility = if (showRetry) View.VISIBLE else View.GONE
    }

    private fun showNetworkError() {
        hideErrorHandlingLayout()
        showLoading(toShow = false)
        binding.errorHandlingLayout.visibility = View.VISIBLE
        binding.offlineIcon.visibility = View.VISIBLE
        binding.errorMessageTv.visibility = View.VISIBLE
        binding.errorMessageTv.text = "Please check your internet and retry"
        binding.retryBtn.visibility = View.VISIBLE
        binding.retryBtn.isClickable = true
    }

    private fun fetchPullRequests() {
        viewModel.getPullRequestFlow()
    }

    private fun setupPagingRv() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pagingAdapter
            adapter = pagingAdapter.withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter { pagingAdapter.retry() },
                footer = LoadingStateAdapter { pagingAdapter.retry() }
            )

            lifecycleScope.launch {
                pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                    run {
                        if (pagingAdapter.itemCount < 1 && loadStates.refresh is LoadState.Error) {
                            if ((loadStates.refresh as LoadState.Error).error is UnknownHostException) {
                                showNetworkError()
                            } else {
                                showError((loadStates.refresh as LoadState.Error).error.message.toString())
                            }
                        }

                        if (loadStates.refresh is LoadState.NotLoading && pagingAdapter.itemCount == 0) {
                            showError(
                                "We dont have any closed pull request for this repo",
                                showRetry = false
                            )
                        }

                        if (pagingAdapter.itemCount > 0) {
                            hideErrorHandlingLayout()
                        }
                    }

                }
            }

        }
    }
}