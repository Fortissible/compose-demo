package com.example.composedemo.utils

import com.example.composedemo.data.model.Anime
import com.example.composedemo.data.remote.response.Data
import com.example.composedemo.data.remote.response.DataItem

object DataMapper {

    fun currentSeasonAnimeResponseToAnimeDomain(animes:List<DataItem?>?): List<Anime> {
        val animeList = mutableListOf<Anime>()
        animes?.map { anime ->
            val genreList = mutableListOf<String>()
            anime?.genres?.map {
                genreList.add(
                    it?.name.toString()
                )
            }

            val studioList = mutableListOf<String>()
            anime?.studios?.map {
                studioList.add(
                    it?.name.toString()
                )
            }

            val animeItem = Anime(
                id = anime?.malId ?: 0,
                images = anime?.images?.jpg?.imageUrl,
                title = anime?.title,
                title_japanese = anime?.titleJapanese,
                type = anime?.type,
                episodes = anime?.episodes ?: 12,
                score = anime?.score,
                synopsis = anime?.synopsis,
                genres = genreList,
                theme_op = null,
                theme_ed = null,
                studios = studioList,
            )
            animeList.add(animeItem)
        }
        return animeList
    }

    fun animeDetailResponseToAnimeDomain(anime: Data?):Anime{
        val genreList = mutableListOf<String>()
        anime?.genres?.map {
            genreList.add(
                it?.name.toString()
            )
        }

        val studioList = mutableListOf<String>()
        anime?.studios?.map {
            studioList.add(
                it?.name.toString()
            )
        }

        val opList = mutableListOf<String>()
        anime?.theme?.openings?.forEach {
            opList.add(it?:"")
        }

        val edList = mutableListOf<String>()
        anime?.theme?.endings?.forEach {
            edList.add(it?:"")
        }

        return Anime(
            id = anime?.malId ?: 0,
            images = anime?.images?.jpg?.imageUrl,
            title = anime?.title,
            title_japanese = anime?.titleJapanese,
            type = anime?.type,
            episodes = anime?.episodes ?: 12,
            score = anime?.score,
            synopsis = anime?.synopsis,
            genres = genreList,
            theme_op = opList,
            theme_ed = edList,
            studios = studioList,
        )
    }
}