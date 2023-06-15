package com.example.composedemo.data.remote

import com.example.composedemo.data.remote.response.Data
import com.example.composedemo.data.remote.response.DataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource (
    private val apiService: JikanService
){
    suspend fun getAnimeCurrentSeasonFromApi(): Flow<ApiResponse<List<DataItem?>>>
    = flow {
        try {
            val animeList = apiService.getAnimeCurrentSeasonFromApi().data
            if (animeList.isNullOrEmpty()) emit(ApiResponse.Empty)
            else emit(ApiResponse.Success(animeList))
        } catch (e:Exception){
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getAnimeDetailFromApi(id:Int): Flow<ApiResponse<Data?>>
    = flow {
        try {
            val animeDetail = apiService.getAnimeDetailFromApi(id).data
            if (animeDetail?.malId == null) emit(ApiResponse.Empty)
            else emit(ApiResponse.Success(animeDetail))
        } catch (e:Exception){
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        fun getRemoteDataSource(apiService: JikanService):RemoteDataSource{
            return RemoteDataSource(apiService)
        }
    }
}