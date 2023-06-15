package fr.aubel.music

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(
	title="API music",
	version = "1.0.0")
)
class MusicApiApplication

fun main(args: Array<String>) {
    runApplication<MusicApiApplication>(*args)
}
