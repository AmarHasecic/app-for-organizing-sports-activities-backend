package ba.unsa.etf.sportevents.controller

import ba.unsa.etf.sportevents.dtos.LoginDTO
import ba.unsa.etf.sportevents.model.User
import ba.unsa.etf.sportevents.repository.UserRepository
import ba.unsa.etf.sportevents.security.JwtUtil
import ba.unsa.etf.sportevents.security.PasswordEncryptor
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api")
class UserController(private val userRepository: UserRepository) {

    private fun generateId(): String{
        var id = UUID.randomUUID().toString().replace("-", "")

        // Check if ID already exists in database
        var existingItem = this.userRepository.findById(id)
        while (existingItem.isPresent) {
            // If ID already exists, recursively call this function to generate a new ID
            id = UUID.randomUUID().toString().replace("-", "")
            existingItem = this.userRepository.findById(id)
        }

        return id;
    }


    @GetMapping("/users")
    fun getUsers(): ResponseEntity<List<User>> {

        return ResponseEntity.ok(this.userRepository.findAll())
    }

    @GetMapping("/user")
    fun getUserById(@RequestHeader("Authorization") token: String): ResponseEntity<User> {
        val userIdFromToken = JwtUtil.getUserIdFromToken(token.substringAfter("Bearer ").trim())

        if (!JwtUtil.validateToken(token.substringAfter("Bearer ").trim())) {
            // Return an HTTP 401 Unauthorized response if the token is invalid or has expired
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val user = userRepository.findById(userIdFromToken)
        if (user.isEmpty) {
            // Return an HTTP 404 Not Found response if the user with the requested id is not found
            return ResponseEntity.notFound().build()
        }

        // Return an HTTP 200 OK response with the user object if everything is successful
        return ResponseEntity.ok(user.get())
    }


    @PostMapping("/user")
    fun createUser(@RequestBody user: User): ResponseEntity<Any> {

        if (userRepository.existsById(user.username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with username ${user.username} already exists")
        }

        user.id = generateId();
        user.password = PasswordEncryptor(user.password).hashPassword()
        return ResponseEntity.ok(this.userRepository.save(user))
    }

    @PutMapping("/user/{id}")
    fun updateUser(@PathVariable id: String, @RequestBody user: User): ResponseEntity<User> {

        val oldUser = this.userRepository.findById(id).orElse(null)
        this.userRepository.deleteById(oldUser.username)

        user.password = PasswordEncryptor(user.password).hashPassword()
        return ResponseEntity.ok(userRepository.save(user))
    }

    @DeleteMapping("/user/{id}")
    fun deleteUser(@PathVariable id: String): ResponseEntity<String> {
        this.userRepository.deleteById(id)
        return ResponseEntity.ok(id)
    }

    @PostMapping("/login")
    fun login(@RequestBody body: LoginDTO): ResponseEntity<Any> {

        val user: User? = userRepository.findByUsername(body.username)
        val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

        if (user != null) {
            val encodedPassword = user.password
            if (passwordEncoder.matches(body.password, encodedPassword)) {


                val jwt = JwtUtil.generateToken(user.id);
                return ResponseEntity.ok(mapOf("jwt" to jwt))

            }
            else  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password incorrect")
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found")
    }

}


