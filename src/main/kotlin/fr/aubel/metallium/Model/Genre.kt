package fr.aubel.metallium.Model

import jakarta.persistence.*

@Entity
data class Genre(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: String,
    var name: String
)
