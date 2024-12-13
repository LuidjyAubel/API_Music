package fr.aubel.metallium.Web

import fr.aubel.metallium.Dao.UserDao
import fr.aubel.metallium.Model.Album
import fr.aubel.metallium.Model.Groupe
import fr.aubel.metallium.Model.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserController {
    @Autowired
    private lateinit var userDao: UserDao
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Operation(summary = "Method get all users")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = User::class)
                )
            ])
    )
    @GetMapping
   // fun index() : List<User> = userDao.findAll()
    fun index(): List<User>{
        val albums = userDao.findAll()
        //profs.forEach { it.fetchGroupe() } // fonctionne pas
        albums.forEach { al ->
                Hibernate.initialize(al.albums)
        }
        return albums
    }

    @Operation(summary = "Method get an user with the id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = User::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"user\":\"not found\"}" )
                )])
    )
    @GetMapping("/{id}")
    fun index(@PathVariable id: String): ResponseEntity<Any> {
        val resUser = userDao.findById(id)
        return if (resUser == null) {
            ResponseEntity(hashMapOf(Pair("user", "not found")), HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity.ok(resUser)
        }
    }

    @Operation(summary = "Method for creating an user")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = User::class)
                )
            ]),
        ApiResponse(responseCode = "400",
            description = "Bad Request",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"user\":\"bad request\"}" )
                )]),
        ApiResponse(responseCode = "304",
            description = "Not Modified",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"user\":\"not created\"}" )
                )]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"user\":\"not found\"}" )
                )])
    )
    @PostMapping
    fun post(@RequestBody(required = false) g: User) : ResponseEntity<Any>{
        if (g== null)
        return  ResponseEntity(hashMapOf<String,String>(Pair("user","invalide")), HttpStatus.BAD_REQUEST)
        try {
            g.password = passwordEncoder.encode(g.password)
            userDao.save(g)
        } catch (e : Exception) {
            return ResponseEntity(hashMapOf<String,String>(Pair("user","not created")), HttpStatus.NOT_MODIFIED)
        }
        var resUser = g.id?.let { userDao.findById(it) }
        if (resUser==null)
        return ResponseEntity(hashMapOf<String,String>(Pair("user","not found")), HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(resUser)
    }

    @Operation(summary = "Method update a user with his id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = User::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"user\":\"not found\"}" )
                )])
    )
    @PutMapping("/{id}")
    fun update(@PathVariable id: String,@RequestBody data:User): ResponseEntity<Any>{

        var resUser = userDao.findById(id)
        if (resUser.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("user","not found")), HttpStatus.NOT_FOUND)
        resUser = Optional.of(data)
        userDao.save(resUser.get())

        return ResponseEntity.ok(data)
    }

    @Operation(summary = "Method get the album of an user with his id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = User::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"user\":\"not found\"}" )
                )])
    )
    @GetMapping("/{id}/album")
    fun getAlbumUser(@PathVariable id: String): ResponseEntity<Any> {
        var p =userDao.findById(id)
        val albums : MutableList<Album> = mutableListOf()
        if (p==null)
            return ResponseEntity(hashMapOf<String,String>(Pair("user","not found")), HttpStatus.NOT_FOUND)
        userDao.findAll().forEach { user ->
            if(user.id == id){
                albums.addAll(user.albums)
            }
        }
        if (albums.isEmpty()){
            return ResponseEntity(hashMapOf<String,String>(Pair("album","not found")), HttpStatus.NOT_FOUND)
        }
        return ResponseEntity.ok(albums)
    }

    @Operation(summary = "Method delete an user with his id")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = User::class)
                )
            ]),
        ApiResponse(responseCode = "404",
            description = "Not Found",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(type = "object",
                        example = "{\"user\":\"not found\"}" )
                )])
    )
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: String):ResponseEntity<Any> {
        var resUser = userDao.findById(id)
        if (resUser.isEmpty)
            return ResponseEntity(hashMapOf<String,String>(Pair("user","not found")), HttpStatus.NOT_FOUND)
        userDao.deleteById(id)
        return ResponseEntity.ok(resUser)
    }
}