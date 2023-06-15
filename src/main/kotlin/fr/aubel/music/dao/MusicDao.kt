package fr.aubel.music.dao

import fr.aubel.music.models.Music
import org.springframework.data.jpa.repository.JpaRepository

interface MusicDao : JpaRepository<Music, String> {
}