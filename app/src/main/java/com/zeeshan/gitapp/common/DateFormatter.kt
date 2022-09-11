package com.zeeshan.gitapp.common

import com.zeeshan.gitapp.domain.model.PullRequest
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    fun formatDate(
        pullRequest: PullRequest
    ): PullRequest {

        pullRequest.closedDate = formatDate(pullRequest.closedDate)
        pullRequest.createdDate = formatDate(pullRequest.createdDate)
        return pullRequest
    }

    private fun formatDate(dateString: String): String{
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-DD'T'HH:MM:SS'Z'", Locale.ENGLISH)
        val date: Date =
            dateFormat.parse(dateString) as Date

        val formatter: DateFormat =
            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

        return formatter.format(date)
    }

}