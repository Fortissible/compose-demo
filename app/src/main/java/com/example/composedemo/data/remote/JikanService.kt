package com.example.composedemo.data.remote

import com.example.composedemo.data.remote.response.AnimeDetailResponse
import com.example.composedemo.data.remote.response.AnimeSeasonResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanService {
    @GET("seasons/now")
    suspend fun getAnimeCurrentSeasonFromApi(
    ): AnimeSeasonResponse

    @GET("anime/{anime_id}/full")
    suspend fun getAnimeDetailFromApi(
        @Path("anime_id") id:Int,
    ): AnimeDetailResponse
}