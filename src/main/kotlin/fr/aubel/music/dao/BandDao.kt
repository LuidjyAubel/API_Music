package fr.aubel.music.dao

import fr.aubel.music.models.Band
import org.springframework.data.jpa.repository.JpaRepository

interface BandDao : JpaRepository<Band, String> {
}