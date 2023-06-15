package fr.aubel.music.dao

import fr.aubel.music.models.Album
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumDao : JpaRepository<Album, String> {
}