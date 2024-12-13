package fr.aubel.metallium.Dao

import fr.aubel.metallium.Model.Album
import fr.aubel.metallium.Model.Version
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VersionDao : JpaRepository<Version, String> {
    fun findByName(name: String): List<Version>?
}