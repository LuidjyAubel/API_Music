package fr.aubel.metallium.Model

import jakarta.persistence.*

@Entity
data class Version(
    @Id
    var id: String,
    var name: String
)
