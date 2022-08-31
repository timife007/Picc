package com.timife.pix.di

import com.timife.pix.data.local.database.PixDb
import com.timife.pix.data.remote.PixApi
import com.timife.pix.domain.repositories.PixRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Provides
    @Singleton
    abstract fun bindPixRepository(
        api: PixApi,
        db: PixDb
    ): PixRepository
}