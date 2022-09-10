package com.zeeshan.gitapp.presentation.paging.utils

import androidx.recyclerview.widget.DiffUtil
import com.zeeshan.gitapp.domain.model.PullRequest

object PagingComparator : DiffUtil.ItemCallback<PullRequest>() {
    override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest) =
        oldItem == newItem
}