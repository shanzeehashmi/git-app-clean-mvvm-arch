package com.zeeshan.gitapp.di

import com.zeeshan.gitapp.data.data_source.remote.GitRepoServiceApi
import com.zeeshan.gitapp.data.repository.GitRepositoryImpl
import com.zeeshan.gitapp.domain.repository.GitRepository
import com.zeeshan.gitapp.domain.use_cases.GetClosedPrUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideClosedPrUseCase(repository: GitRepository): GetClosedPrUseCase{
        return GetClosedPrUseCase(repository)
    }

    @Provides
    fun provideGitRepo(api: GitRepoServiceApi): GitRepository{
        return GitRepositoryImpl(api)
    }

}