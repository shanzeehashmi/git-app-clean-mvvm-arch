package com.zeeshan.gitapp.paging_test

import com.zeeshan.gitapp.domain.model.PullRequest
import com.zeeshan.gitapp.domain.model.UserProfile

object PullRequestFactory {

    val mockPullRequestList = listOf(
        PullRequest(
            1,
            1,
            "titleOne",
            "2021/01/01",
            "2021/02,02",
            UserProfile("dummyUser", "dummyUrl")
        ),
        PullRequest(
            2,
            2,
            "titleOne",
            "2021/01/01",
            "2021/02,02",
            UserProfile("dummyUser", "dummyUrl")
        ),
        PullRequest(
            3,
            3,
            "titleOne",
            "2021/01/01",
            "2021/02,02",
            UserProfile("dummyUser", "dummyUrl")
        ),
        PullRequest(
            4,
            4,
            "titleOne",
            "2021/01/01",
            "2021/02,02",
            UserProfile("dummyUser", "dummyUrl")
        ),
        PullRequest(
            5,
            5,
            "titleOne",
            "2021/01/01",
            "2021/02,02",
            UserProfile("dummyUser", "dummyUrl")
        )
    )
}