package fr.aubel.metallium.Config

import fr.aubel.metallium.Dao.UserDao
import fr.aubel.metallium.Model.User
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class DefaultUserConfig {

    @Bean
    fun defaultUserRunner(userRepository: UserDao, passwordEncoder: PasswordEncoder): CommandLineRunner {
        return CommandLineRunner {
            // Vérifier si l'utilisateur admin existe déjà
            val existingUser = userRepository.findByUsername("admin")
            if (existingUser == null) {
                // Créer un utilisateur par défaut
                val defaultUser = User(
                    id = "1",
                    username = "admin",
                    email = "admin@jmail.com",
                    picture = "#",
                    password = passwordEncoder.encode("admin"),  // Encoder le mot de passe
                    role = "ADMIN"
                )
                userRepository.save(defaultUser)
                println("Utilisateur 'admin' créé avec succès")
            }
        }
    }
}
