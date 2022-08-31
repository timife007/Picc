package com.timife.pix.di

import android.app.Application
import androidx.room.Room
import com.timife.pix.common.Constants
import com.timife.pix.data.local.database.PixDb
import com.timife.pix.data.remote.PixApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePixApi(): PixApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providePixDatabase(app: Application): PixDb {
        return Room.databaseBuilder(
            app,
            PixDb::class.java,
            "pixdb.db"
        ).build()
    }

}