package fr.aubel.music.dao

import fr.aubel.music.models.Genre
import org.springframework.data.jpa.repository.JpaRepository

interface GenreDao : JpaRepository<Genre, String> {
    //override fun findById(id: String): Optional<Genre>
}