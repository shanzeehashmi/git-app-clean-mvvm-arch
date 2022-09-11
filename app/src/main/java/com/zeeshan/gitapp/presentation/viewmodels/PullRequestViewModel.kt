package com.zeeshan.gitapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.zeeshan.gitapp.common.DateFormatter
import com.zeeshan.gitapp.common.UIState
import com.zeeshan.gitapp.domain.model.PullRequest
import com.zeeshan.gitapp.domain.use_cases.PrUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PullRequestViewModel @Inject constructor(
    private val prUseCase: PrUseCase
) : ViewModel() {

    private val _openPullRequest = MutableLiveData<UIState<PagingData<PullRequest>>>()
    var openPullRequest: LiveData<UIState<PagingData<PullRequest>>> = _openPullRequest

    private var loadJob: Job? = null

    fun getClosedPullRequestFlow() {
        _openPullRequest.postValue(UIState.Loading())
        loadJob?.cancel()
        loadJob = viewModelScope.launch(Dispatchers.IO) {

            prUseCase.getClosedPrUseCase.invoke().map { pagingData -> pagingData.map { DateFormatter.formatDate(it) } }
                .cachedIn(viewModelScope)
                .collect {
                    _openPullRequest.postValue(UIState.Success(it))
                }
        }
    }

}