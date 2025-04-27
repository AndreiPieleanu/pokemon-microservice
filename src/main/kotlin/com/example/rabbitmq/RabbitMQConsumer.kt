package com.example.rabbitmq

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class RabbitMQConsumer {
    @RabbitListener(queues = ["pokemon-create-queue"])
    fun handlePostCreatedEvent(event: PokemonCreatedEvent) {
        println("received post created event: $event")
    }
}
