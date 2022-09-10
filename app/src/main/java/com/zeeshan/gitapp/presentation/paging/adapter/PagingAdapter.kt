package com.zeeshan.gitapp.presentation.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeeshan.gitapp.databinding.LayoutClosedPullRequestItemBinding
import com.zeeshan.gitapp.domain.model.PullRequest
import com.zeeshan.gitapp.presentation.paging.utils.PagingComparator

class PagingAdapter :
    PagingDataAdapter<PullRequest, PagingAdapter.PagingViewHolder>(PagingComparator) {


    inner class PagingViewHolder(private val binding: LayoutClosedPullRequestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PullRequest) = with(binding) {
            binding.pullrequest = item
        }
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder =
        PagingViewHolder(
            LayoutClosedPullRequestItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


}