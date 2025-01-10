package fr.aubel.metallium.Web

import fr.aubel.metallium.Dao.GenreDao
import fr.aubel.metallium.Model.Genre
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/genre")
class GenreController {
    @Autowired
    private lateinit var genreDao: GenreDao

    @Operation(summary = "Method get all genre")
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
    fun index() : List<Genre> = genreDao.findAll()

    @Operation(summary = "Method get a genre with the id")
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
    fun index(@PathVariable id: Long): ResponseEntity<Any> {
        val resgenre = genreDao.findById(id)
        return if (resgenre == null) {
            ResponseEntity(hashMapOf(Pair("genre", "not found")), HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity.ok(resgenre)
        }
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
                )]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"genre\":\"not found\"}" )
                )])
    )
    @PostMapping
    fun post(@RequestBody(required = false) p: Genre?) : ResponseEntity<Any> {
        if (p== null)
            return  ResponseEntity(hashMapOf<String,String>(Pair("genre","bad request")), HttpStatus.BAD_REQUEST)
        try {
            genreDao.save(p)
        } catch (e : Exception) {
            return ResponseEntity(hashMapOf<String,String>(Pair("genre","not created")), HttpStatus.NOT_MODIFIED)
        }

        var resGenre = p.id?.let { genreDao.findById(it) }
        if (resGenre==null)
            return ResponseEntity(hashMapOf<String,String>(Pair("genre","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(resGenre)
    }

    @Operation(summary = "Method update a genre with his id")
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
    fun update(@PathVariable id: Long,@RequestBody data:Genre): ResponseEntity<Any>{

        var resGenre = genreDao.findById(id)
        if (resGenre.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("Genre","not found")), HttpStatus.NOT_FOUND)
        val upGenre = resGenre.get()
        upGenre.name = data.name
        genreDao.save(upGenre)
        return ResponseEntity.ok(resGenre)
    }
    @Operation(summary = "Method delete a genre with his id")
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
    fun delete(@PathVariable id: Long):ResponseEntity<Any> {
        var resultatgenre = genreDao.findById(id)
        if (resultatgenre.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("genre","not found")), HttpStatus.NOT_FOUND)
        genreDao.deleteById(id)
        return ResponseEntity.ok(resultatgenre)
    }
}