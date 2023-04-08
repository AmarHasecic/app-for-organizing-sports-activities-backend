package ba.unsa.etf.sportevents.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class PasswordEncryptor(
    private val password : String
){

     fun hashPassword(): String{
        val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
         return passwordEncoder.encode(password)
    }
}