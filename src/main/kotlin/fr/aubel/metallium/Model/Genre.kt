package fr.aubel.metallium.Model

import jakarta.persistence.*

@Entity
data class Genre(
    @Id
    var id: String,
    var name: String
)
