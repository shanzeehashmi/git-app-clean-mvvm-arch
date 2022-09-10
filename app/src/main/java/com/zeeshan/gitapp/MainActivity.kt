package com.zeeshan.gitapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeeshan.gitapp.databinding.ActivityMainBinding
import com.zeeshan.gitapp.presentation.paging.adapter.LoadingStateAdapter
import com.zeeshan.gitapp.presentation.paging.adapter.PagingAdapter
import com.zeeshan.gitapp.presentation.viewmodels.PullRequestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PullRequestViewModel by viewModels()
    private val pagingAdapter: PagingAdapter by lazy {
        PagingAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupPagingRv()
        if(savedInstanceState == null) {
            fetchPullRequests()
        }

        viewModel.openPullRequest.observe(this){
            lifecycleScope.launch(){
                pagingAdapter.submitData(it)
            }

        }


    }

    private fun fetchPullRequests(){
        viewModel.getPullRequestFlow()
    }

    private fun setupPagingRv(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pagingAdapter
            adapter = pagingAdapter.withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter { pagingAdapter.retry() },
                footer = LoadingStateAdapter { pagingAdapter.retry() }
            )
        }
    }
}