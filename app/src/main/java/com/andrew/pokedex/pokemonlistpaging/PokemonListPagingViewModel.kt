package com.andrew.pokedex.pokemonlistpaging

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.palette.graphics.Palette
import com.andrew.pokedex.repository.PokemonPagingSource
import com.andrew.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListPagingViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    val pokemons = Pager(
        PagingConfig(pageSize = 20)
    ) {
        PokemonPagingSource(pokemonRepository)
    }.flow.cachedIn(viewModelScope)

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { color ->
                onFinish(Color(color))
            }
        }
    }
}