package com.example.controller

import com.example.datalayer.IPokemonRepository
import com.example.dto.Pokemon
import com.example.rabbitmq.RabbitMQProducer
import com.example.servicelayer.IPokemonService
import com.example.servicelayer.PokemonService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
class PokemonControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val pokemonRepository: IPokemonRepository = mock()
    private val rabbitMq: RabbitMQProducer = mock()
    private val pokemonService: IPokemonService = PokemonService(pokemonRepository, rabbitMq)

    @Test
    fun create() {

        // Arrange
        val pokemon = Pokemon(1, "Pikachu", "Gold")
        whenever(pokemonRepository.save(pokemon)).thenReturn(pokemon)
        whenever(pokemonService.addNewPokemon(pokemon)).thenReturn(pokemon)
        // Act
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/pokemon").contentType(APPLICATION_JSON_VALUE).content(
                """
                                {
                                    "name": "Pikachu",
                                    "type": "Gold"
                                }
                                
                                """.trimIndent()
            )
        )
            .andDo(print())
            .andExpect(status().`is`(200))
            .andReturn()

        // Assert
        val expectedJson = "{" +
                "\"id\":1," +
                "\"name\":\"Pikachu\"," +
                "\"type\":\"Gold\"" +
                "}"
        val actualJson = mvcResult.response.contentAsString
        assertEquals(expectedJson, actualJson)
    }
}