package com.zeeshan.gitapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zeeshan.gitapp.domain.model.PullRequest
import com.zeeshan.gitapp.domain.repository.GitRepository
import com.zeeshan.gitapp.domain.usecases.GetClosedPrUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PullRequestViewModel @Inject constructor(
    private val closedPrUseCase: GetClosedPrUseCase
) : ViewModel() {

    private val _openPullRequest = MutableLiveData<PagingData<PullRequest>>()
    var openPullRequest: LiveData<PagingData<PullRequest>> = _openPullRequest

    private var loadJob: Job? = null

    fun getPullRequestFlow() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch(Dispatchers.IO) {

            closedPrUseCase.invoke()
                .cachedIn(viewModelScope)
                .collect {
                    _openPullRequest.postValue(it)
                }
        }
    }

}