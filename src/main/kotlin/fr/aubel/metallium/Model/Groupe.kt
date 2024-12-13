package fr.aubel.metallium.Model

import jakarta.persistence.*

@Entity
data class Groupe(
    @Id
    var id: String,
    var name: String
)
