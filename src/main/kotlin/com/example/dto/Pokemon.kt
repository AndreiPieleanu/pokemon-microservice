package com.example.dto

import jakarta.persistence.Column
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
    @ToString.Exclude
    val id: Long = 0
    @Column(unique = true)
    @ToString.Include
    var name: String = ""
    @ToString.Exclude
    var type: String = ""
}