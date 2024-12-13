package fr.aubel.metallium.Web


import fr.aubel.metallium.Dao.VersionDao
import fr.aubel.metallium.Model.Groupe
import fr.aubel.metallium.Model.Version
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
@RequestMapping("/api/version")
class VersionController {
    @Autowired
    private lateinit var versionDao: VersionDao

    @Operation(summary = "Method get all version")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Version::class)
                )
            ])
    )
    @GetMapping
    fun index() : List<Version> = versionDao.findAll()

    @Operation(summary = "Method get a version with the id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Version::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"version\":\"not found\"}" )
                )])
    )
    @GetMapping("/{id}")
    fun index(@PathVariable id: String): ResponseEntity<Any> {
        val resVersion = versionDao.findById(id)
        return if (resVersion == null) {
            ResponseEntity(hashMapOf(Pair("version", "not found")), HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity.ok(resVersion)
        }
    }

    @Operation(summary = "Method for creating a version")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Version::class)
                )
            ]),
        ApiResponse(responseCode = "400",
            description = "Bad Request",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"version\":\"bad request\"}" )
                )]),
        ApiResponse(responseCode = "304",
            description = "Not Modified",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"version\":\"not created\"}" )
                )]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"version\":\"not found\"}" )
                )])
    )
    @PostMapping
    fun post(@RequestBody(required = false) p: Version?) : ResponseEntity<Any> {
        if (p== null)
            return  ResponseEntity(hashMapOf<String,String>(Pair("version","bad request")), HttpStatus.BAD_REQUEST)
        try {
            versionDao.save(p)
        } catch (e : Exception) {
            return ResponseEntity(hashMapOf<String,String>(Pair("version","not created")), HttpStatus.NOT_MODIFIED)
        }
        var resVersion = p.id?.let { versionDao.findById(it) }
        if (resVersion==null)
            return ResponseEntity(hashMapOf<String,String>(Pair("version","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(resVersion)
    }

    @Operation(summary = "Method update a version with his id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Version::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"version\":\"not found\"}" )
                )])
    )
    @PutMapping("/{id}")
    fun update(@PathVariable id: String,@RequestBody data:Version): ResponseEntity<Any>{

        var resVersion = versionDao.findById(id)
        if (resVersion.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("version","not found")), HttpStatus.NOT_FOUND)
        resVersion = Optional.of(data)
        versionDao.save(resVersion.get())

        return ResponseEntity.ok(data)
    }

    @Operation(summary = "Method delete a version with his id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Version::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"version\":\"not found\"}" )
                )])
    )
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: String):ResponseEntity<Any> {
        var resultatversion = versionDao.findById(id)
        if (resultatversion.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("version","not found")), HttpStatus.NOT_FOUND)
        versionDao.deleteById(id)
        return ResponseEntity.ok(resultatversion)
    }
}