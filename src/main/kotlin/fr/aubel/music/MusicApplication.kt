package fr.aubel.music

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@SpringBootApplication
@OpenAPIDefinition(info = Info(
	title="API music",
	version = "1.0.0")
)
class MusicApplication

fun main(args: Array<String>) {
	runApplication<MusicApplication>(*args)
}
