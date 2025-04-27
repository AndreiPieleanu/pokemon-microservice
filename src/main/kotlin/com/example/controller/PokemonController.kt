package com.example.controller

import com.example.dto.Pokemon
import com.example.servicelayer.*
import lombok.AllArgsConstructor
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pokemon")
@AllArgsConstructor
class PokemonController(private val service: PokemonService) {
    @GetMapping
    fun getAll(): List<Pokemon> = service.getAllPokemons()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Pokemon = service.getPokemonById(id)

    @GetMapping("/external")
    fun getExternalPokemons(): Any? = service.getExternalPokemons()

    @PostMapping
    fun create(@RequestBody pokemon: Pokemon): Pokemon = service.addNewPokemon(pokemon)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody updated: Pokemon): Pokemon = service.updatePokemon(id, updated)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.deletePokemonById(id)
}