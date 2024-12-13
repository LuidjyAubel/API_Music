package fr.aubel.metallium.Model

import jakarta.persistence.*

@Entity
data class Album(
    @Id
    var id: String,
    var name: String,
    @ManyToOne
    @JoinColumn(name = "groupe_id", referencedColumnName = "id")
    var groupe: Groupe,
    var release: String,
    @OneToMany(fetch = FetchType.EAGER)
@JoinTable(
    name = "version_album", // Table de jointure
    joinColumns = [JoinColumn(name = "album_id")],
    inverseJoinColumns = [JoinColumn(name = "version_id")]
)
var version: MutableList<Version> = mutableListOf()
){
    fun fetchVersion() {
        version.size // Chargement de la collection avant la fermeture de la session Hibernate
    }
}
