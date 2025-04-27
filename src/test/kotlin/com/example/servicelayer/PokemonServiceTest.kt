package com.example.servicelayer

import com.example.datalayer.IPokemonRepository
import com.example.dto.Pokemon
import com.example.rabbitmq.RabbitMQProducer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@SpringBootTest
class PokemonServiceTest {

    private val pokemonRepository: IPokemonRepository = mock()
    private val rabbitMq: RabbitMQProducer = mock()
    private val pokemonService: IPokemonService = PokemonService(pokemonRepository, rabbitMq)
    private val restTemplate: RestTemplate = mock()

    @Test
    fun getAllPokemonsShouldGetPokemonsWhenListHasElement() {
        // Arrange
        val pokemon = Pokemon(1, "Pikachu", "Gold")
        whenever(pokemonRepository.findAll()).thenReturn(listOf(pokemon))
        // Act
        val result = pokemonService.getAllPokemons()
        // Assert
        assertNotNull(result)
        assertEquals(result.size, 1)
    }

    @Test
    fun getAllPokemonsShouldGetEmptyListWhenListEmpty() {
        // Arrange
        whenever(pokemonRepository.findAll()).thenReturn(listOf())
        // Act
        val result = pokemonService.getAllPokemons()
        // Assert
        assertNotNull(result)
        assertEquals(result.size, 0)
    }

    @Test
    fun getPokemonByIdShouldReturnPokemonWhenItExists() {
        // Arrange
        val id: Long = 1
        val pokemon = Pokemon(1, "Pikachu", "Gold")
        whenever(pokemonRepository.findById(id)).thenReturn(Optional.of(pokemon))
        // Act
        val result = pokemonService.getPokemonById(id)
        // Assert
        assertNotNull(result)
        assertNotNull(result.name)
        assertNotNull(result.type)
        assertEquals(result.name, pokemon.name)
        assertEquals(result.type, pokemon.type)
    }

    @Test
    fun getPokemonByIdShouldThrowExceptionWhenNoFound() {
        // Arrange
        val id: Long = 1
        whenever(pokemonRepository.findById(id)).thenReturn(Optional.empty())
        // Act & Assert
        assertThrows<Exception> {
            pokemonService.getPokemonById(id)
        }
    }

    @Test
    fun addNewPokemonShouldAddPokemon() {
        // Arrange
        val pokemon = Pokemon(1, "Pikachu", "Gold")
        whenever(pokemonRepository.save(pokemon)).thenReturn(pokemon)
        // Act
        val result = pokemonService.addNewPokemon(pokemon)
        // Assert
        assertNotNull(result)
        assertNotNull(result.name)
        assertNotNull(result.type)
        assertEquals(result.name, pokemon.name)
        assertEquals(result.type, pokemon.type)
    }

    @Test
    fun updatePokemonShouldUpdatePokemonWhenPokemonExists() {
        // Arrange
        val id: Long = 1
        val pokemon = Pokemon(1, "Pikachu", "Gold")
        val updatedPokemon = Pokemon(1, "New Pikachu", "New Gold")
        whenever(pokemonRepository.findById(id)).thenReturn(Optional.of(pokemon))
        whenever(pokemonRepository.save(pokemon)).thenReturn(pokemon)
        // Act
        val result = pokemonService.updatePokemon(id, updatedPokemon)
        // Assert
        assertNotNull(result)
        assertNotNull(result.name)
        assertNotNull(result.type)
        assertEquals(result.name, updatedPokemon.name)
        assertEquals(result.type, updatedPokemon.type)
    }

    @Test
    fun updatePokemonShouldThrowExceptionWhenPokemonDoesNotExist() {
        // Arrange
        val id: Long = 1
        val updatedPokemon = Pokemon(1, "New Pikachu", "New Gold")
        whenever(pokemonRepository.findById(id)).thenReturn(Optional.empty())
        // Act & Assert
        assertThrows<Exception> {
            pokemonService.updatePokemon(id, updatedPokemon)
        }
    }

    @Test
    fun deletePokemonByIdWhenPokemonExists() {
        // Arrange
        val id: Long = 1
        whenever(pokemonRepository.deleteById(id)).thenThrow(IllegalStateException())
        // Act & Assert
        assertThrows<IllegalStateException> {
            pokemonService.deletePokemonById(id)
        }
    }

    @Test
    fun getExternalPokemonsWhenURLValid(){
        // Arrange
        val url = "https://pokeapi.co/api/v2/pokemon/ditto"
        whenever(restTemplate.getForObject<Any?>(url)).thenReturn("")
        // Act
        val result = pokemonService.getExternalPokemons()
        // Assert
        assertNotNull(result)
    }
}