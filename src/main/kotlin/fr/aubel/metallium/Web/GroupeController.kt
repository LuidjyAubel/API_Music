package fr.aubel.metallium.Web

import fr.aubel.metallium.Dao.GroupeDao
import fr.aubel.metallium.Model.Groupe
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
@RequestMapping("/api/groupe")
class GroupeController {
    @Autowired
    private lateinit var groupeDao: GroupeDao

    @Operation(summary = "Method get all groupe")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Groupe::class)
                )
            ])
    )
    @GetMapping
    fun index() : List<Groupe> = groupeDao.findAll()

    @Operation(summary = "Method get a groupe with the id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Groupe::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"groupe\":\"not found\"}" )
                )])
    )
    @GetMapping("/{id}")
    fun index(@PathVariable id: Long): ResponseEntity<Any> {
        val resgroupe = groupeDao.findById(id)
        return if (resgroupe == null) {
            ResponseEntity(hashMapOf(Pair("groupe", "not found")), HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity.ok(resgroupe)
        }
    }

    @Operation(summary = "Method for creating a groupe")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Groupe::class)
                )
            ]),
        ApiResponse(responseCode = "400",
            description = "Bad Request",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"groupe\":\"bad request\"}" )
                )]),
        ApiResponse(responseCode = "304",
            description = "Not Modified",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"groupe\":\"not created\"}" )
                )]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"groupe\":\"not found\"}" )
                )])
    )
    @PostMapping
    fun post(@RequestBody(required = false) p: Groupe?) : ResponseEntity<Any> {
        if (p== null)
            return  ResponseEntity(hashMapOf<String,String>(Pair("groupe","bad request")), HttpStatus.BAD_REQUEST)
        try {
            groupeDao.save(p)
        } catch (e : Exception) {
            return ResponseEntity(hashMapOf<String,String>(Pair("groupe","not created")), HttpStatus.NOT_MODIFIED)
        }

        var resGroupe = p.id?.let { groupeDao.findById(it) }
        if (resGroupe==null)
            return ResponseEntity(hashMapOf<String,String>(Pair("groupe","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(resGroupe)
    }

    @Operation(summary = "Method update a groupe with his id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Groupe::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"groupe\":\"not found\"}" )
                )])
    )
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long,@RequestBody data:Groupe): ResponseEntity<Any>{

        var resGroupe = groupeDao.findById(id)
        if (resGroupe.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("groupe","not found")), HttpStatus.NOT_FOUND)
        resGroupe = Optional.of(data)
        groupeDao.save(resGroupe.get())

        return ResponseEntity.ok(data)
    }

    @Operation(summary = "Method delete a groupe with his id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Groupe::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"Groupe\":\"not found\"}" )
                )])
    )
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Long):ResponseEntity<Any> {
        var resultatgroupe = groupeDao.findById(id)
        if (resultatgroupe.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("groupe","not found")), HttpStatus.NOT_FOUND)
        groupeDao.deleteById(id)
        return ResponseEntity.ok(resultatgroupe)
    }
}