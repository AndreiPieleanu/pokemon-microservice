package com.example.rabbitmq

data class PokemonCreatedEvent(
    val id: Long,
    val name: String,
    val type: String
)