package fr.aubel.metallium.Dao

import fr.aubel.metallium.Model.Album
import fr.aubel.metallium.Model.Groupe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface GroupeDao : JpaRepository<Groupe, String> {
    fun findByName(name: String): List<Groupe>?
}