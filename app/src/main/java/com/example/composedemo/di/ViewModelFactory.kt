package com.example.composedemo.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composedemo.data.repository.MangaRepository
import com.example.composedemo.screen.mangadetail.DetailViewModel
import com.example.composedemo.screen.mangalist.ListViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val mangaRepository: MangaRepository
    ): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(mangaRepository) as T
        }
        else if (modelClass.isAssignableFrom(ListViewModel::class.java)){
            return ListViewModel(mangaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance : ViewModelFactory ?= null
        fun getInstance(context: Context) : ViewModelFactory=
            instance?: synchronized(this){
                instance?: ViewModelFactory(
                    Injection.provideMangaRepository()
                )
            }.also { instance = it}
    }
}