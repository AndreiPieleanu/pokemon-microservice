package com.example.rabbitmq

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Bean
    fun pokemonExchange(): TopicExchange {
        return TopicExchange("pokemon-exchange")
    }

    @Bean
    fun pokemonCreateQueue(): Queue {
        return Queue("pokemon-create-queue", false)
    }

    @Bean
    fun pokemonUpdateQueue(): Queue {
        return Queue("pokemon-update-queue", false)
    }

    @Bean
    fun pokemonDeleteQueue(): Queue {
        return Queue("pokemon-delete-queue", false)
    }

    @Bean
    fun pokemonCreateBinding(pokemonCreateQueue: Queue?, pokemonExchange: TopicExchange?): Binding {
        return BindingBuilder.bind(pokemonCreateQueue).to(pokemonExchange).with("pokemon.created")
    }

    @Bean
    fun pokemonUpdateBinding(pokemonUpdateQueue: Queue?, pokemonExchange: TopicExchange?): Binding {
        return BindingBuilder.bind(pokemonUpdateQueue).to(pokemonExchange).with("pokemon.updated")
    }

    @Bean
    fun pokemonDeleteBinding(pokemonDeleteQueue: Queue?, pokemonExchange: TopicExchange?): Binding {
        return BindingBuilder.bind(pokemonDeleteQueue).to(pokemonExchange).with("pokemon.deleted")
    }

    // Message converter for both exchanges
    @Bean
    fun messageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    // RabbitTemplate for sending messages
    @Bean
    fun template(connectionFactory: ConnectionFactory?): AmqpTemplate {
        val template = RabbitTemplate(connectionFactory!!)
        template.messageConverter = messageConverter()
        return template
    }
}