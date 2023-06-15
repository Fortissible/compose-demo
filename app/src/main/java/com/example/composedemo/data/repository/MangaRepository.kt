package com.example.composedemo.data.repository

import com.example.composedemo.data.Resource
import com.example.composedemo.data.model.Anime
import com.example.composedemo.data.remote.ApiResponse
import com.example.composedemo.data.remote.JikanService
import com.example.composedemo.data.remote.RemoteDataSource
import com.example.composedemo.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class MangaRepository(
    private val remoteDataSource: RemoteDataSource
) {
    fun getAnimeCurrentSeasonFromApi(): Flow<Resource<List<Anime>>> = flow {
        emit(Resource.Loading())
        when(val movies = remoteDataSource.getAnimeCurrentSeasonFromApi().first()){
            is ApiResponse.Empty -> {
                val emptyMovie = DataMapper.currentSeasonAnimeResponseToAnimeDomain(null)
                emit(Resource.Success(emptyMovie))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(movies.errorMessage))
            }
            is ApiResponse.Success -> {
                val moviesDomain =  DataMapper.currentSeasonAnimeResponseToAnimeDomain(movies.data)
                emit(Resource.Success(moviesDomain))
            }
        }
    }

    fun getAnimeDetailFromApi(
        id: Int
    ): Flow<Resource<Anime>> = flow {
        emit(Resource.Loading())
        when(val animeDetail = remoteDataSource.getAnimeDetailFromApi(id).first()){
            is ApiResponse.Empty -> {
                val emptyAnimeDetail = DataMapper.animeDetailResponseToAnimeDomain(null)
                emit(Resource.Success(emptyAnimeDetail))
            }
            is ApiResponse.Error -> emit(Resource.Error(animeDetail.errorMessage))
            is ApiResponse.Success -> {
                val animeDetailDomain = DataMapper.animeDetailResponseToAnimeDomain(animeDetail.data)
                emit(Resource.Success(animeDetailDomain))
            }
        }
    }
}