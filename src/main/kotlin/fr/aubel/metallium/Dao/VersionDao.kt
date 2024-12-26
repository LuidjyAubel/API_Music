package fr.aubel.metallium.Dao

import fr.aubel.metallium.Model.Version
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VersionDao : JpaRepository<Version, Long> {
    fun findByName(name: String): List<Version>?
}