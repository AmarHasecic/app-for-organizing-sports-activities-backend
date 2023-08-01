package ba.unsa.etf.sportevents.controller

import ba.unsa.etf.sportevents.model.LoginDTO
import ba.unsa.etf.sportevents.model.User
import ba.unsa.etf.sportevents.repository.UserRepository
import ba.unsa.etf.sportevents.security.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.MessageDigest
import java.util.*


fun hashPassword(password: String): String {
    // Convert the password to bytes (required by the MessageDigest)
    val passwordBytes = password.toByteArray()

    // Create a SHA-256 MessageDigest instance
    val sha256Digest = MessageDigest.getInstance("SHA-256")

    // Update the digest with the password bytes
    val hashedBytes = sha256Digest.digest(passwordBytes)

    // Convert the hashed bytes to a hexadecimal string
    val hashedPassword = StringBuilder()
    for (byte in hashedBytes) {
        val hexString = Integer.toHexString(0xFF and byte.toInt())
        if (hexString.length == 1) {
            hashedPassword.append('0')
        }
        hashedPassword.append(hexString)
    }

    return hashedPassword.toString()
}

@RestController
@RequestMapping("/api")
class UserController(private val userRepository: UserRepository) {

    private fun generateId(): String{
        var id = UUID.randomUUID().toString().replace("-", "")

        var existingItem = this.userRepository.findById(id)
        while (existingItem.isPresent) {
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
    fun getUser(@RequestHeader("Authorization") token: String): ResponseEntity<User> {
        val userIdFromToken = JwtUtil.getIdFromToken(token.substringAfter("Bearer ").trim())

        if (!JwtUtil.validateToken(token.substringAfter("Bearer ").trim())) {

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
        user.password = hashPassword(user.password)
        return ResponseEntity.ok(this.userRepository.save(user))
    }

    @PutMapping("/user/{id}")
    fun updateUser(@PathVariable id: String, @RequestBody user: User): ResponseEntity<User> {

        val oldUser = this.userRepository.findById(id).orElse(null)

        if(user.password.isBlank()){
            user.password = oldUser.password
        }
        else{
            user.password = hashPassword(user.password)
        }
        this.userRepository.deleteById(oldUser.username)
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

        if (user != null) {
            val storedHashedPassword = user.password
            val enteredPasswordHash = hashPassword(body.password)

            return if (storedHashedPassword == enteredPasswordHash) {
                val jwt = JwtUtil.generateToken(user.id)
                ResponseEntity.ok(mapOf("jwt" to jwt))
            } else {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password incorrect")
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found")
    }
      
}


