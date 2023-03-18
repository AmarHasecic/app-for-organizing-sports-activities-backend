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

    @GetMapping("/")
    fun getUsers(): ResponseEntity<List<User>> {

        return ResponseEntity.ok(this.userRepository.findAll())
    }

    @GetMapping("/{username}")
    fun getUsers(@PathVariable username: String): ResponseEntity<User> {

        return ResponseEntity.ok(this.userRepository.findById(username).orElse(null))
    }

    @PostMapping("/")
    fun createUser(@RequestBody user: User): ResponseEntity<Any>  {

        if (userRepository.existsById(user.username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with username ${user.username} already exists")
        }

        //password hashing
        val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
        user.password = passwordEncoder.encode(user.password)


        return ResponseEntity.ok(this.userRepository.save(user))
    }

    @PutMapping("/{username}")
    fun updateUser(@PathVariable username: String,@RequestBody user: User): ResponseEntity<User>  {

        val oldUser = this.userRepository.findById(username).orElse(null)
        this.userRepository.deleteById(oldUser.username)
        return ResponseEntity.ok(userRepository.save(user))
    }

    @DeleteMapping("/{username}")
        fun deleteUser(@PathVariable username: String): ResponseEntity<String>{
        this.userRepository.deleteById(username)
            return ResponseEntity.ok(username)
        }

}