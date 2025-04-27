package com.example.servicelayer

import com.example.datalayer.IPokemonRepository
import com.example.dto.Pokemon
import com.example.rabbitmq.PokemonCreatedEvent
import com.example.rabbitmq.RabbitMQProducer
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Service
@AllArgsConstructor
class PokemonService(private val repository: IPokemonRepository, private val eventProducer: RabbitMQProducer) : IPokemonService {
    override fun getAllPokemons(): List<Pokemon> {
        println("inside service!!!!!")
        return repository.findAll()
    }
    override fun getPokemonById(id: Long): Pokemon{
        return repository.findById(id).orElseThrow()
    }
    override fun addNewPokemon(pokemon: Pokemon): Pokemon{
        eventProducer.publishPokemonCreatedEvent(PokemonCreatedEvent(pokemon.id, pokemon.name, pokemon.type))
        return repository.save(pokemon)
    }
    override fun updatePokemon(id: Long, pokemon: Pokemon): Pokemon{
        val currentPokemon = repository.findById(id).orElseThrow()
        currentPokemon.name = pokemon.name
        currentPokemon.type = pokemon.type
        eventProducer.publishPokemonUpdatedEvent(PokemonCreatedEvent(currentPokemon.id, currentPokemon.name, currentPokemon.type))
        repository.save(currentPokemon)
        return currentPokemon
    }
    override fun deletePokemonById(id: Long){
        eventProducer.publishPokemonDeletedEvent(PokemonCreatedEvent(id, "", ""))
        repository.deleteById(id)
    }

    override fun getExternalPokemons(): Any? {
        val restTemplate = RestTemplate()
        val url = "https://pokeapi.co/api/v2/pokemon/ditto"
        return restTemplate.getForObject<Any>(url)
    }
}