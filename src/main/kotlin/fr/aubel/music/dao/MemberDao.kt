package fr.aubel.music.dao

import fr.aubel.music.models.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberDao : JpaRepository<Member, String> {
}