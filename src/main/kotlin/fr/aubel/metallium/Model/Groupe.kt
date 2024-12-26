package fr.aubel.metallium.Model

import jakarta.persistence.*

@Entity
data class Groupe(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String
)
