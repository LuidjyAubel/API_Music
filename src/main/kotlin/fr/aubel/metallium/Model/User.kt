package fr.aubel.metallium.Model

import jakarta.persistence.*

@Entity
@Table(name = "app_user")  // Remplacez 'user' par un autre nom vcar User mot clé SQL
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var username: String,
    var picture : String,
    var email: String,
    var password: String,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_albums", // Table de jointure
        joinColumns = [JoinColumn(name = "user_id")], // Colonne pour l'ID de l'utilisateur
        inverseJoinColumns = [JoinColumn(name = "album_id")] // Colonne pour l'ID de l'album
    )
    var albums: MutableList<Album> = mutableListOf(), // Utiliser MutableList
    var role: String
){
    fun fetchAlbum() {
        albums.size // Chargement de la collection avant la fermeture de la session Hibernate
    }
}
