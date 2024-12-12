package fr.aubel.metallium.Model

import jakarta.persistence.*

@Entity
data class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: String,
    var name: String,
    @ManyToOne
    @JoinColumn(name = "groupe_id", referencedColumnName = "id")
    var groupe: Groupe,
    var year: String,
    @ManyToOne
    @JoinColumn(name = "version_id", referencedColumnName = "id")
    var version: Version
)
