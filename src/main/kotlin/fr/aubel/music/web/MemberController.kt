package fr.aubel.music.web

import fr.aubel.music.dao.MemberDao
import fr.aubel.music.models.Genre
import fr.aubel.music.models.Member
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
@RequestMapping("/member")
class MemberController {
    @Autowired
    private lateinit var memberDao: MemberDao

    @Operation(summary = "Method get all member")
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
    fun index(): List<Member> = memberDao.findAll()

    @Operation(summary = "Method get a member with the id of the genre")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Member::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"member\":\"not found\"}" )
                )])
    )
    @GetMapping("/{id}")
    fun index(@PathVariable id: String): ResponseEntity<Any> {
        var m = memberDao.findById(id)
        if (m==null)
            return ResponseEntity(hashMapOf<String,String>(Pair("member","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(m)
    }

    @Operation(summary = "Method for creating a member")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Member::class)
                )
            ]),
        ApiResponse(responseCode = "400",
            description = "Bad Request",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"member\":\"bad request\"}" )
                )]),
        ApiResponse(responseCode = "304",
            description = "Not Modified",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"member\":\"not created\"}" )
                )])
    )
    @PostMapping
    fun post(@RequestBody(required = false) g: Member?) : ResponseEntity<Any>{
        if (g== null)
            return  ResponseEntity(hashMapOf<String,String>(Pair("member","bad request")), HttpStatus.BAD_REQUEST)
        try {
            memberDao.save(g)
        } catch (e : Exception) {
            return ResponseEntity(hashMapOf<String,String>(Pair("groupe","not created")), HttpStatus.NOT_MODIFIED)
        }
        var resultMember = g.Id?.let { memberDao.findById(it) }
        return ResponseEntity.ok(resultMember)
    }

    @Operation(summary = "Method for delete a member with the id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Member::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"member\":\"not found\"}" )
                )])
    )
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: String):ResponseEntity<Any> {
        var resultMember = memberDao.findById(id)
        if (resultMember.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("member","not found")), HttpStatus.NOT_FOUND)
        memberDao.deleteById(id)
        return ResponseEntity.ok(resultMember)
    }

    @Operation(summary = "Method for update the member with an id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = Member::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"member\":\"not found\"}" )
                )])
    )
    @PutMapping("/{id}")
    fun update(@PathVariable id: String,@RequestBody data:Member): ResponseEntity<Any>{

        var resultMember = memberDao.findById(id)
        if (resultMember.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("member","not found")), HttpStatus.NOT_FOUND)
        resultMember = Optional.of(data)
        memberDao.save(resultMember.get())
        return ResponseEntity.ok(data)
    }
}