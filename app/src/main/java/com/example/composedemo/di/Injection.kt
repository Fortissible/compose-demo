package com.example.composedemo.di

import com.example.composedemo.data.remote.APIconfig
import com.example.composedemo.data.remote.RemoteDataSource
import com.example.composedemo.data.repository.MangaRepository

object Injection {
    fun provideMangaRepository() : MangaRepository{
        val apiService = APIconfig.getApiService()
        val remoteDataSource = RemoteDataSource.getRemoteDataSource(apiService)
        return MangaRepository(remoteDataSource)
    }
}