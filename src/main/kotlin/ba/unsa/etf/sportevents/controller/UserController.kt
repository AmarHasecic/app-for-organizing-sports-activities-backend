package ba.unsa.etf.sportevents.controller

import ba.unsa.etf.sportevents.model.User
import ba.unsa.etf.sportevents.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users")
class UserController(private val userRepository: UserRepository) {

    private fun hashPassword(password: String): String{

        val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
        return passwordEncoder.encode(password)
    }

    @GetMapping("")
    fun getUsers(): ResponseEntity<List<User>> {

        return ResponseEntity.ok(this.userRepository.findAll())
    }

    @GetMapping("/{username}")
    fun getUsers(@PathVariable username: String): ResponseEntity<User> {

        return ResponseEntity.ok(this.userRepository.findById(username).orElse(null))
    }

    @PostMapping("")
    fun createUser(@RequestBody user: User): ResponseEntity<Any> {

        if (userRepository.existsById(user.username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with username ${user.username} already exists")
        }

        user.password = hashPassword(user.password)
        return ResponseEntity.ok(this.userRepository.save(user))
    }

    @PutMapping("/{username}")
    fun updateUser(@PathVariable username: String, @RequestBody user: User): ResponseEntity<User> {

        val oldUser = this.userRepository.findById(username).orElse(null)
        this.userRepository.deleteById(oldUser.username)

        user.password = hashPassword(user.password)
        return ResponseEntity.ok(userRepository.save(user))
    }

    @DeleteMapping("/{username}")
    fun deleteUser(@PathVariable username: String): ResponseEntity<String> {
        this.userRepository.deleteById(username)
        return ResponseEntity.ok(username)
    }

    @GetMapping("/login/{username}/{password}")
    fun login(@PathVariable username: String, @PathVariable password: String): ResponseEntity<Any> {

        val user: User? = userRepository.findById(username).orElse(null)
        val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

        if (user != null) {
            val encodedPassword = user.password
            if (passwordEncoder.matches(password, encodedPassword)) {
                return ResponseEntity.ok(user)
            }
            else  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password incorrect")
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")
    }
}


