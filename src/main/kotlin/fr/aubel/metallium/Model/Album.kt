package fr.aubel.metallium.Model

import jakarta.persistence.*


// Add auto increment to id
@Entity
data class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String,
    @ManyToOne
    @JoinColumn(name = "groupe_id", referencedColumnName = "id")
    var groupe: Groupe,
    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    var genre: Genre,
    var release: String,
    @ManyToMany(fetch = FetchType.EAGER)
@JoinTable(
    name = "version_album", // Table de jointure
    joinColumns = [JoinColumn(name = "album_id")],
    inverseJoinColumns = [JoinColumn(name = "version_id")],
)
var version: MutableList<Version> = mutableListOf()
){
    fun fetchVersion() {
        version.size // Chargement de la collection avant la fermeture de la session Hibernate
    }
}
