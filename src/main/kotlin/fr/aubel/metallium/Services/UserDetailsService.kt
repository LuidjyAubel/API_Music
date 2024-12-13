package fr.aubel.metallium.Services

import fr.aubel.metallium.Dao.UserDao
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Service
class UserDetailsService(private val userRepository : UserDao, private val passwordEncoder: PasswordEncoder) : UserDetailsService {
    var logger: Logger = LoggerFactory.getLogger(UserDetailsService::class.java)

    override fun loadUserByUsername(username: String): UserDetails {
        if (userRepository.findByUsername(username) == null) {
            logger.info("utilisateur non trouvez !")
        }
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("user not found")

        // Vérifie que le mot de passe fourni correspond à celui stocké (haché)
        if (!passwordEncoder.matches(user.password, passwordEncoder.encode(user.password))) {
            throw BadCredentialsException("Invalid credentials")
        }

        val authorities = mutableListOf<SimpleGrantedAuthority>()
        authorities.add(SimpleGrantedAuthority("ROLE_${user.role}"))

        return User(user.username, user.password, authorities)
}
}