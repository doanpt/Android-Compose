package com.andrew.pokedex.repository

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.andrew.pokedex.data.remote.models.PokedexListEntry
import com.andrew.pokedex.util.Constants
import com.andrew.pokedex.util.Resource
import java.util.Locale

class PokemonPagingSource(
    private val pokemonRepository: PokemonRepository
) : PagingSource<Int, PokedexListEntry>() {
    override fun getRefreshKey(state: PagingState<Int, PokedexListEntry>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokedexListEntry> {
        return try {
            val curPage = params.key ?: 0
            val response =
                pokemonRepository.getPokemonList(Constants.PAGE_SIZE, offset = curPage * 20)
            when (response) {
                is Resource.Success -> {
                    if (response.data?.results.isNullOrEmpty()) {
                        LoadResult.Error(Throwable("EMPTY"))
                    } else {
                        val pokedexEntries = response.data?.results?.mapIndexed { index, entry ->
                            val number = if (entry.url.endsWith("/")) {
                                entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                            } else {
                                entry.url.takeLastWhile { it.isDigit() }
                            }
                            val url =
                                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                            PokedexListEntry(
                                entry.name.capitalize(Locale.ROOT),
                                url,
                                number.toInt()
                            )
                        }
                        Page(
                            data = pokedexEntries ?: emptyList(),
                            prevKey = if (curPage == 0) null else curPage - 1,
                            nextKey = if (response.data?.results?.isEmpty() == false) curPage + 1 else null
                        )
                    }

                }

                is Resource.Error -> {
                    LoadResult.Error(Throwable(response.message))
                }

                is Resource.Loading -> {
                    throw IllegalArgumentException()
                }
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}