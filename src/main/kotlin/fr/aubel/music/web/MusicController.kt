package fr.aubel.music.web

import fr.aubel.music.dao.MusicDao
import fr.aubel.music.models.Genre
import fr.aubel.music.models.Music
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
@RequestMapping("/music")
class MusicController {
    @Autowired
    private lateinit var musicDao: MusicDao

    @Operation(summary = "Method get all music")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Music::class)
                )
            ])
    )
    @GetMapping
    fun index(): List<Music> = musicDao.findAll()

    @Operation(summary = "Method get a music with the id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Music::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"formation\":\"not found\"}" )
                )])
    )
    @GetMapping("/{id}")
    fun index(@PathVariable id: String): ResponseEntity<Any> {
        val resMusic = musicDao.findById(id)
        return if (resMusic == null) {
            ResponseEntity(hashMapOf(Pair("music", "not found")), HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity.ok(resMusic)
        }
    }

    @Operation(summary = "Method update a music with his id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Music::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"music\":\"not found\"}" )
                )])
    )
    @PutMapping("/{id}")
    fun update(@PathVariable id: String,@RequestBody data:Music): ResponseEntity<Any>{

        var resMusic = musicDao.findById(id)
        if (resMusic.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("music","not found")), HttpStatus.NOT_FOUND)
        resMusic = Optional.of(data)
        musicDao.save(resMusic.get())

        return ResponseEntity.ok(data)
    }

    @Operation(summary = "Method delete a music with his id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Music::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"music\":\"not found\"}" )
                )])
    )
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: String):ResponseEntity<Any> {
        var resMusic = musicDao.findById(id)
        if (resMusic.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("music","not found")), HttpStatus.NOT_FOUND)
        musicDao.deleteById(id)
        return ResponseEntity.ok(resMusic)
    }
}