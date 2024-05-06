package com.myapp.wallpaperapp.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.myapp.wallpaperapp.data.WallpaperRepositoryImpl
import com.myapp.wallpaperapp.data.api.ImageApi
import com.myapp.wallpaperapp.domain.repositories.WallpaperRepository
import com.myapp.wallpaperapp.utils.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {

        @Provides
        @Singleton
        fun getImages(): ImageApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ImageApi::class.java)
        }

    }

    @Binds
    @Singleton
    abstract fun bindPicsumRepository(repositoryImpl: WallpaperRepositoryImpl): WallpaperRepository
}