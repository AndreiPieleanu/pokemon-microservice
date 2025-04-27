package com.example.datalayer

import com.example.dto.Pokemon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IPokemonRepository : JpaRepository<Pokemon, Long> {

}