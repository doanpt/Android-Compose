package com.andrew.pokedex.pokemondetails

import androidx.lifecycle.ViewModel
import com.andrew.pokedex.data.remote.response.Pokemon
import com.andrew.pokedex.repository.PokemonRepository
import com.andrew.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }
}