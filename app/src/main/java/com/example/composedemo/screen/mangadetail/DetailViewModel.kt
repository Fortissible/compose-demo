package com.example.composedemo.screen.mangadetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.composedemo.data.Resource
import com.example.composedemo.data.model.Anime
import com.example.composedemo.data.repository.MangaRepository

class DetailViewModel(
    private val mangaRepository: MangaRepository
):ViewModel() {

    val getAnimeDetailFromApi : (id:Int) -> LiveData<Resource<Anime>> = { id ->
        mangaRepository.getAnimeDetailFromApi(id).asLiveData()
    }

}