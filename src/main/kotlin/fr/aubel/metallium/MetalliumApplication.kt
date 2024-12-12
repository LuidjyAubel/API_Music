package fr.aubel.metallium

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(
	title="Metallium API",
	version = "1.0.0")
)
class MetalliumApplication

fun main(args: Array<String>) {
	runApplication<MetalliumApplication>(*args)
}
