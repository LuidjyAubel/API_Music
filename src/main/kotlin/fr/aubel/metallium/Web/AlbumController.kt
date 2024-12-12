package fr.aubel.metallium.Web

import fr.aubel.metallium.Dao.AlbumDao
import fr.aubel.metallium.Model.Album
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
@RequestMapping("/api/albums")
class AlbumController {
    @Autowired
    private lateinit var albumDao: AlbumDao

    @Operation(summary = "Method get all album")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Album::class)
                )
            ]
        )
    )
    @GetMapping
    fun index(): List<Album> = albumDao.findAll()

    @Operation(summary = "Method get an album with the id")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Album::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(
                        type = "object",
                        example = "{\"album\":\"not found\"}"
                    )
                )]
        )
    )
    @GetMapping("/{id}")
    fun index(@PathVariable id: String): ResponseEntity<Any> {
        val resAlbum = albumDao.findById(id)
        return if (resAlbum == null) {
            ResponseEntity(hashMapOf(Pair("album", "not found")), HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity.ok(resAlbum)
        }
    }

    @Operation(summary = "Method for creating a album")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Album::class)
                )
            ]),
        ApiResponse(responseCode = "400",
            description = "Bad Request",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"album\":\"bad request\"}" )
                )]),
        ApiResponse(responseCode = "304",
            description = "Not Modified",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"album\":\"not created\"}" )
                )]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"album\":\"not found\"}" )
                )])
    )
    @PostMapping
    fun post(@RequestBody(required = false) p: Album?) : ResponseEntity<Any> {
        if (p== null)
            return  ResponseEntity(hashMapOf<String,String>(Pair("album","bad request")), HttpStatus.BAD_REQUEST)
        try {
            albumDao.save(p)
        } catch (e : Exception) {
            return ResponseEntity(hashMapOf<String,String>(Pair("album","not created")), HttpStatus.NOT_MODIFIED)
        }

        var resAlbum = p.id?.let { albumDao.findById(it) }
        if (resAlbum==null)
            return ResponseEntity(hashMapOf<String,String>(Pair("album","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(resAlbum)
    }

    @Operation(summary = "Method update an album with his id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Album::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"album\":\"not found\"}" )
                )])
    )
    @PutMapping("/{id}")
    fun update(@PathVariable id: String,@RequestBody data:Album): ResponseEntity<Any>{

        var resAlbum = albumDao.findById(id)
        if (resAlbum.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("album","not found")), HttpStatus.NOT_FOUND)
        resAlbum = Optional.of(data)
        albumDao.save(resAlbum.get())

        return ResponseEntity.ok(data)
    }

    @Operation(summary = "Method delete a album with his id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Album::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"album\":\"not found\"}" )
                )])
    )
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: String):ResponseEntity<Any> {
        var resultatAlbum = albumDao.findById(id)
        if (resultatAlbum.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("album","not found")), HttpStatus.NOT_FOUND)
        albumDao.deleteById(id)
        return ResponseEntity.ok(resultatAlbum)
    }
}