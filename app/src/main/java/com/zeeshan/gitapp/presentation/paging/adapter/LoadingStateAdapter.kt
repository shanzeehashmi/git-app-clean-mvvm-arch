package com.zeeshan.gitapp.presentation.paging.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeeshan.gitapp.databinding.LayoutPagingLoadingStateBinding

class LoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {


    class LoadingStateViewHolder(
        private val binding: LayoutPagingLoadingStateBinding,
        retry: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryBtn.setOnClickListener { retry.invoke() }
        }

        fun bindState(loadState: LoadState) = with(binding) {

            binding.progressBar.visibility = View.GONE
            binding.retryBtn.visibility = View.GONE
            binding.pagingErrorMessage.visibility = View.GONE

            when (loadState) {
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is LoadState.Error -> {
                    binding.pagingErrorMessage.text = loadState.error.message
                    binding.retryBtn.visibility = View.VISIBLE
                }

                else -> {}
            }

        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder = LoadingStateViewHolder(
        LayoutPagingLoadingStateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), retry
    )

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bindState(loadState)
    }
}


