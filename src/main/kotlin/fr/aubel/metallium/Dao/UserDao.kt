package fr.aubel.metallium.Dao

import fr.aubel.metallium.Model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDao : JpaRepository<User, String> {
    fun findByUserId(id: String): User?
    fun findByEmail(email: String): User?
}