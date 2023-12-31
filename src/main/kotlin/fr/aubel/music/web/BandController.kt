package fr.aubel.music.web

import fr.aubel.music.dao.BandDao
import fr.aubel.music.models.Band
import fr.aubel.music.models.Genre
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

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
    fun index(): List<Band>{
        val resBand = bandDao.findAll()
        resBand.forEach { form ->
            Hibernate.initialize(form.members)
        }
        return resBand
    }

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
    @Operation(summary = "Method for creating an band")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Band::class)
                )
            ]),
        ApiResponse(responseCode = "400",
            description = "Bad Request",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"band\":\"bad request\"}" )
                )]),
        ApiResponse(responseCode = "304",
            description = "Not Modified",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"band\":\"not created\"}" )
                )]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"band\":\"not found\"}" )
                )])
    )
    @PostMapping
    fun post(@RequestBody(required = false) p: Band?) : ResponseEntity<Any> {
        if (p== null)
            return  ResponseEntity(hashMapOf<String,String>(Pair("band","bad request")), HttpStatus.BAD_REQUEST)
        try {
            bandDao.save(p)
        } catch (e : Exception) {
            return ResponseEntity(hashMapOf<String,String>(Pair("formation","not created")), HttpStatus.NOT_MODIFIED)
        }

        var resBand = p.Id?.let { bandDao.findById(it) }
        if (resBand==null)
            return ResponseEntity(hashMapOf<String,String>(Pair("band","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(resBand)
    }
    @Operation(summary = "Method update a band with his id")
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
                        example = "{\"band\":\"not found\"}" )
                )])
    )
    @PutMapping("/{id}")
    fun update(@PathVariable id: String,@RequestBody data:Band): ResponseEntity<Any>{

        var resBand = bandDao.findById(id)
        if (resBand.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("band","not found")), HttpStatus.NOT_FOUND)
        resBand = Optional.of(data)
        bandDao.save(resBand.get())

        return ResponseEntity.ok(data)
    }
    @Operation(summary = "Method delete a band with his id")
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
                        example = "{\"band\":\"not found\"}" )
                )])
    )
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: String):ResponseEntity<Any> {
        var resBand = bandDao.findById(id)
        if (resBand.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("band","not found")), HttpStatus.NOT_FOUND)
        bandDao.deleteById(id)
        return ResponseEntity.ok(resBand)
    }
}