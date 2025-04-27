package com.example.servicelayer

import com.example.dto.Pokemon


interface IPokemonService {
    fun getAllPokemons(): List<Pokemon>
    fun getPokemonById(id: Long): Pokemon
    fun addNewPokemon(pokemon: Pokemon): Pokemon
    fun updatePokemon(id: Long, pokemon: Pokemon): Pokemon
    fun deletePokemonById(id: Long)
    fun getExternalPokemons(): Any?
}