package com.zeeshan.gitapp.di

import com.zeeshan.gitapp.domain.use_cases.GetClosedPrUseCase
import com.zeeshan.gitapp.domain.use_cases.PrUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(getClosedPrUseCase: GetClosedPrUseCase): PrUseCase {
        return PrUseCase(getClosedPrUseCase)
    }

}