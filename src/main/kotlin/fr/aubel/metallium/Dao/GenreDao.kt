package fr.aubel.metallium.Dao

import fr.aubel.metallium.Model.Genre
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GenreDao : JpaRepository<Genre, Long> {
    fun findByName(name: String): List<Genre>?
}