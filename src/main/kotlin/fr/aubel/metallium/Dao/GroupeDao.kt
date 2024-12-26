package fr.aubel.metallium.Dao

import fr.aubel.metallium.Model.Groupe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface GroupeDao : JpaRepository<Groupe, Long> {
    fun findByName(name: String): List<Groupe>?
}