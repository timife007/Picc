package com.timife.pix.di

import com.timife.pix.data.remote.PixApi
import com.timife.pix.data.repositories.PixRepositoryImpl
import com.timife.pix.domain.repositories.PixRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun bindPixRepository(
        api: PixApi): PixRepository {
        return PixRepositoryImpl(api)
    }
}