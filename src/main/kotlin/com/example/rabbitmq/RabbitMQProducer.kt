package com.example.rabbitmq

import lombok.AllArgsConstructor
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class RabbitMQProducer {
    private val rabbitTemplate: RabbitTemplate? = null

    fun publishPokemonCreatedEvent(pokemonCreatedEvent: PokemonCreatedEvent) {
        rabbitTemplate?.convertAndSend("pokemon-exchange", "pokemon.created", pokemonCreatedEvent)
        println("fired new pokemon created: $pokemonCreatedEvent")
    }

    fun publishPokemonUpdatedEvent(pokemonCreatedEvent: PokemonCreatedEvent) {
        rabbitTemplate?.convertAndSend("pokemon-exchange", "pokemon.updated", pokemonCreatedEvent)
        println("fired new pokemon updated: $pokemonCreatedEvent")
    }

    fun publishPokemonDeletedEvent(pokemonCreatedEvent: PokemonCreatedEvent) {
        rabbitTemplate?.convertAndSend("pokemon-exchange", "pokemon.deleted", pokemonCreatedEvent)
        println("fired new pokemon deleted: $pokemonCreatedEvent")
    }
}
