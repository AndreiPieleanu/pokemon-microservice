package com.example.dto

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.*

@Entity
@Data
@AllArgsConstructor
class Pokemon(i: Int, s: String, s1: String) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    var name: String = ""
    var type: String = ""
}