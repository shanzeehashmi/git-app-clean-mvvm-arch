package com.zeeshan.gitapp.di

import com.zeeshan.gitapp.common.Constant
import com.zeeshan.gitapp.data.data_source.remote.GitRepoServiceApi
import com.zeeshan.gitapp.data.data_source.remote.retrofit.adapters.networkrequest.NetworkResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()
    }


    @Provides
    fun provideGitRepoService(retrofit: Retrofit): GitRepoServiceApi {
        return retrofit.create(GitRepoServiceApi::class.java)
    }

}
