package fr.aubel.metallium.Model

import jakarta.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: String,
    var username: String,
    var picture : String,
    var email: String,
    var password: String,
    @ManyToOne
    @JoinColumn(name = "album_id")
    var album: Album? = null,
)
