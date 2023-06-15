package com.example.composedemo.screen.mangalist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.composedemo.data.Resource
import com.example.composedemo.data.model.Anime
import com.example.composedemo.data.repository.MangaRepository

class ListViewModel(
    private val mangaRepository: MangaRepository
): ViewModel() {

    val getAnimeCurrentSeasonFromApi : () -> LiveData<Resource<List<Anime>>> = {
        mangaRepository.getAnimeCurrentSeasonFromApi().asLiveData()
    }
}