package fr.aubel.metallium.Dao

import fr.aubel.metallium.Model.Album
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlbumDao : JpaRepository<Album, String> {
    fun findByName(name: String): List<Album>?
}