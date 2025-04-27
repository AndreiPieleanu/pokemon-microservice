package com.example

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableRabbit
@SpringBootApplication
class PokemonMicroserviceApplication

fun main(args: Array<String>) {
	runApplication<PokemonMicroserviceApplication>(*args)
}
