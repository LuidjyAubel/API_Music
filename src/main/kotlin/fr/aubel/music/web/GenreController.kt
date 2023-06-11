package fr.aubel.music.web

import fr.aubel.music.dao.GenreDao
import fr.aubel.music.models.Genre
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/genre")
class GenreController {
    @Autowired
    private lateinit var genreDao: GenreDao

    @Operation(summary = "Method get all Genre")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Genre::class)
                )
            ])
    )
    @GetMapping
    fun index(): List<Genre> = genreDao.findAll()

    @Operation(summary = "Method get a genre with the id of the genre")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Genre::class)
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
        var g =genreDao.findById(id)
        if (g==null)
            return ResponseEntity(hashMapOf<String,String>(Pair("genre","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(g)
    }

    @Operation(summary = "Method for creating a genre")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Genre::class)
                )
            ]),
        ApiResponse(responseCode = "400",
            description = "Bad Request",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"genre\":\"bad request\"}" )
                )]),
        ApiResponse(responseCode = "304",
            description = "Not Modified",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"genre\":\"not created\"}" )
                )])
    )
    @PostMapping
    fun post(@RequestBody(required = false) g: Genre?) : ResponseEntity<Any>{
        if (g== null)
            return  ResponseEntity(hashMapOf<String,String>(Pair("genre","bad request")), HttpStatus.BAD_REQUEST)
        try {
            genreDao.save(g)
        } catch (e : Exception) {
            return ResponseEntity(hashMapOf<String,String>(Pair("genre","not created")), HttpStatus.NOT_MODIFIED)
        }
        var resultGenre = g.Id?.let { genreDao.findById(it) }
        return ResponseEntity.ok(resultGenre)
    }

    @Operation(summary = "Method for delete a genre with the id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Genre::class)
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
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: String):ResponseEntity<Any> {
        var resultGenre = genreDao.findById(id)
        if (resultGenre.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("genre","not found")), HttpStatus.NOT_FOUND)
        genreDao.deleteById(id)
        return ResponseEntity.ok(resultGenre)
    }

    @Operation(summary = "Method for update the genre with an id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Genre::class)
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
    @PutMapping("/{id}")
    fun update(@PathVariable id: String,@RequestBody data:Genre): ResponseEntity<Any>{

        var resultGenre = genreDao.findById(id)
        if (resultGenre.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("genre","not found")), HttpStatus.NOT_FOUND)
        resultGenre = Optional.of(data)
        genreDao.save(resultGenre.get())
        return ResponseEntity.ok(data)
    }
}