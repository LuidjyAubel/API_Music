package fr.aubel.music.web

import fr.aubel.music.dao.BandDao
import fr.aubel.music.models.Band
import fr.aubel.music.models.Genre
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/band")
class BandController {
    @Autowired
    private lateinit var bandDao : BandDao

    @Operation(summary = "Method get all band")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Band::class)
                )
            ])
    )
    @GetMapping
    fun index(): List<Band> = bandDao.findAll()

    @Operation(summary = "Method get a genre with the id of the band")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Band::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"genre\":\"not found\"}" )
                )])
    )
    @GetMapping("/{id}")
    fun index(@PathVariable id: String): ResponseEntity<Any> {
        var g =bandDao.findById(id)
        if (g==null)
            return ResponseEntity(hashMapOf<String,String>(Pair("band","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(g)
    }

}